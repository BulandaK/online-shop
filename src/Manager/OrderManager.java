package Manager;

import Models.Order.Invoice;
import Models.Order.Order;
import Models.Product.Product;
import MyException.EmptyCartException;
import Services.Discount.DiscountStrategy;
import Services.InvoicePersistence;
import Services.PaymentService;
import State.GlobalState;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages the order processing workflow, including cart operations and order execution.
 * <p>
 * Now supports dynamic registration of discount codes.
 * </p>
 *
 * @author OnlineShop Team
 * @version 1.3
 */
public class OrderManager {

    private final ProductManager productManager;
    private final CartManager cartManager;
    private final PaymentService paymentService;
    private final Scanner scanner = new Scanner(System.in);
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * Registry storing valid discount codes and their corresponding strategies.
     * Key: Discount Code (String), Value: Discount Strategy (Algorithm).
     */
    private final Map<String, DiscountStrategy> discountRegistry = new HashMap<>();

    public OrderManager() {
        this.productManager = GlobalState.getProductManager();
        this.cartManager = new CartManager();
        this.paymentService = new PaymentService();
    }

    /**
     * Registers a new discount code in the system.
     *
     * @param code     The code the user must type (e.g., "SUMMER2025").
     * @param strategy The logic for the discount (e.g., new PercentageDiscount(0.10)).
     */
    public void registerDiscount(String code, DiscountStrategy strategy) {
        discountRegistry.put(code.toUpperCase(), strategy);
    }

    public void showProducts() {
        productManager.showInventory();
    }

    public void addToCart(Order order) {
        try {
            Long id = getIdFromUser("\n\nWybierz id produktu, który chcesz dodać do koszyka:");
            cartManager.addByIdToCart(order.getCart(), id);
            cartManager.showCart(order.getCart());
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    public void removeFromCart(Order order) {
        if (order.getCart().isEmpty()) {
            throw new EmptyCartException("Nie można usunąć z koszyka - jest pusty!");
        }
        Long id = getIdFromUser("\n\nWybierz id produktu, który chcesz usunąć:");
        cartManager.removeByIdFromCart(order.getCart(), id);
        cartManager.showCart(order.getCart());
    }

    /**
     * Prompts the user to enter a discount code.
     * Looks up the code in the registry and applies the strategy if found.
     *
     * @param order The order to apply the discount to.
     */
    public void addDiscount(Order order) {
        System.out.println("\nPodaj kod rabatowy:");
        String code = scanner.nextLine().trim().toUpperCase();

        if (discountRegistry.containsKey(code)) {
            DiscountStrategy strategy = discountRegistry.get(code);
            order.getCart().setDiscountStrategy(strategy);

            System.out.println("Kod " + code + " aktywny!");
            System.out.println("Aktualna wartość koszyka: " + order.getCart().sumPrices());
        } else {
            System.out.println("Kod nieprawidłowy lub nieaktywny.");
        }
    }

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

    public void close() {
        System.out.println("Zamykanie puli wątków...");
        executorService.shutdown();
    }

    // --- PRIVATE METHODS ---

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
        scanner.nextLine(); // consume newline
        return id;
    }
}