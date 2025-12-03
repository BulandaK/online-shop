package Manager;

import Models.Order.Invoice;
import Models.Order.Order;
import Models.Product.Product;
import MyException.EmptyCartException;
import Services.InvoicePersistence;
import Services.PaymentService;
import State.GlobalState;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages the entire lifecycle of an order execution.
 * <p>
 * This class is responsible for coordinating inventory reservation, payment processing,
 * and invoice generation. It uses asynchronous processing to handle orders
 * without blocking the user interface.
 * </p>
 *
 */
public class OrderManager {

    private final ProductManager productManager;
    private final CartManager cartManager;
    private final PaymentService paymentService;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Thread pool for processing orders asynchronously.
     * Configured with 5 threads to handle multiple customers concurrently.
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * Initializes the OrderManager with dependencies.
     * Gets the singleton instance of ProductManager.
     */
    public OrderManager() {
        this.productManager = GlobalState.getProductManager();
        this.cartManager = new CartManager();
        this.paymentService = new PaymentService();
    }

    /**
     * Displays all available products in the inventory to the console.
     */
    public void showProducts() {
        productManager.showInventory();
    }

    /**
     * Prompts the user for a product ID and adds it to the current order's cart.
     *
     * @param order The current order object containing the cart.
     */
    public void addToCart(Order order) {
        try {
            Long id = getIdFromUser("\n\nWybierz id produktu, który chcesz dodać do koszyka:");
            cartManager.addByIdToCart(order.getCart(), id);
            cartManager.showCart(order.getCart());
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for a product ID and removes it from the current order's cart.
     *
     * @param order The current order object containing the cart.
     * @throws EmptyCartException if the cart is already empty.
     */
    public void removeFromCart(Order order) {
        if (order.getCart().isEmpty()) {
            throw new EmptyCartException("Nie można usunąć z koszyka - jest pusty!");
        }
        Long id = getIdFromUser("\n\nWybierz id produktu, który chcesz usunąć:");
        cartManager.removeByIdFromCart(order.getCart(), id);
        cartManager.showCart(order.getCart());
    }

    /**
     * Executes the order process asynchronously.
     * <p>
     * The process consists of three steps:
     * <ol>
     * <li><b>Reservation:</b> Checks stock availability and reserves products (synchronized).</li>
     * <li><b>Payment:</b> Simulates payment processing (long-running task).</li>
     * <li><b>Finalization:</b> Generates an invoice on success, or returns items to stock on failure (rollback).</li>
     * </ol>
     *
     * @param order The order to be executed.
     */
    public void executeOrder(Order order) {
        if (order.getCart().isEmpty()) {
            System.out.println("Koszyk jest pusty. Nie można złożyć zamówienia.");
            return;
        }

        System.out.println("\n--- Rozpoczynam proces zamówienia (w tle)... ---");
        System.out.println("--- Ty możesz nadal korzystać z aplikacji! ---");

        CompletableFuture
                .supplyAsync(() -> tryReserveStock(order), executorService)
                .thenCompose(reservationSuccess -> {
                    if (!reservationSuccess) {
                        return CompletableFuture.completedFuture(false);
                    }
                    return CompletableFuture.supplyAsync(() ->
                            paymentService.processPayment(order.getCart().sumPrices()), executorService
                    );
                })
                .thenAcceptAsync(isPaid -> {
                    if (isPaid) {
                        handleOrderSuccess(order);
                    } else {
                        handleOrderFailure(order);
                    }
                }, executorService);
    }

    /**
     * Shuts down the executor service to allow the application to exit.
     * Should be called when the application is closing.
     */
    public void close() {
        System.out.println("Zamykanie puli wątków...");
        executorService.shutdown();
    }

    /**
     * Attempts to reserve stock for products in the cart.
     * <p>
     * This method is synchronized on {@code productManager} to prevent Race Conditions
     * when multiple threads try to buy the same last item.
     * </p>
     *
     * @param order The order containing products to reserve.
     * @return {@code true} if reservation was successful, {@code false} if any product is unavailable.
     */
    private boolean tryReserveStock(Order order) {
        synchronized (productManager) {
            System.out.println(" [LOGISTYKA] Sprawdzam dostępność i rezerwuję towar...");
            List<Product> products = order.getCart().getProducts();

            for (Product p : products) {
                if (!p.isAvailable()) {
                    System.out.println(" [LOGISTYKA] Brak towaru: " + p.getName());
                    return false;
                }
            }

            for (Product p : products) {
                p.setAvailableQuantity(p.getAvailableQuantity() - 1);
            }
            System.out.println(" [LOGISTYKA] Towar zarezerwowany.");
            return true;
        }
    }

    /**
     * Handles successful order completion: generates invoice, saves it, and clears the cart.
     *
     * @param order The successfully paid order.
     */
    private void handleOrderSuccess(Order order) {
        System.out.println(" [SKLEP] Płatność OK. Finalizuję zamówienie...");

        Invoice invoice = new Invoice(
                "FV/" + System.currentTimeMillis(),
                order.getClient(),
                order.getCart(),
                0.23
        );

        order.getCart().clear();

        System.out.println(" [SKLEP] Zamówienie zrealizowane pomyślnie!");

        try {
            InvoicePersistence.saveInvoice(invoice, "invoices/invoices.txt");
            System.out.println(" [SYSTEM] Faktura zapisana.");
        } catch (IOException e) {
            System.err.println(" [SYSTEM] Błąd zapisu faktury: " + e.getMessage());
        }
    }

    /**
     * Handles order failure (Rollback): returns reserved items back to the inventory.
     *
     * @param order The failed order.
     */
    private void handleOrderFailure(Order order) {
        System.out.println(" [SKLEP] Płatność odrzucona lub anulowana. Zwalniam rezerwację...");

        synchronized (productManager) {
            for (Product p : order.getCart().getProducts()) {
                p.setAvailableQuantity(p.getAvailableQuantity() + 1);
            }
        }
        System.out.println(" [SKLEP] Rezerwacja zwolniona. Towar wrócił na półkę.");
    }

    private Long getIdFromUser(String message) {
        System.out.println(message);
        while (!scanner.hasNextLong()) {
            System.out.println("To nie jest liczba. Spróbuj ponownie:");
            scanner.next();
        }
        Long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }
}