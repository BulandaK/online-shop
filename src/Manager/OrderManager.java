package Manager;

import Models.Order.Invoice;
import Models.Order.Order;
import Models.Product.Product;
import MyException.EmptyCartException;
import Services.InvoicePersistence;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Optional.ofNullable;

public class OrderManager {

    private final ProductManager productManager;
    private final Scanner scanner = new Scanner(System.in);
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public OrderManager() {
        this.productManager = State.GlobalState.getProductManager();
    }

    public void showProducts() {
        productManager.showInventory();
    }

    public void addToCart(Order order) {
        try {
            Long id = getIdFromUser("\n\nwybierz id produktu ktory chcesz dodac do koszyka");

            order.getCart().add(id);
            order.getCart().show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void removeFromCart(Order order) {

        if (order.getCart().isEmpty()) {
            throw new EmptyCartException("Nie można usunać z koszyka jest pusty!");
        }

        Long id = getIdFromUser("\n\nwybierz id produktu ktory chcesz usunac z koszyka");

        ofNullable(order.getCart().remove(id)).ifPresent(product -> {
            System.out.println("usuneles z koszyka product: " + product);
            order.getCart().show();
        });

    }

    public void executeOrder(Order order) throws EmptyCartException {
        if (order.getCart().isEmpty()) {
            throw new EmptyCartException("Nie można wykonać zamówienia, koszyk jest pusty!");
        }

        canExecuteOrder(order).thenAcceptAsync(canExecute -> {
            if (!canExecute) {
                System.out.println("Nie można wykonać zamówienia – produkt niedostępny!");
                return;
            }

            InvoiceManager.generateInvoice(order);

        });
    }

    private CompletableFuture<Boolean> canExecuteOrder(Order order) {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> productsInCart = order.getCart().getProducts();
            for (Product product : productsInCart) {
                if (!product.isAvailable()) {
                    return false;
                } else {
                    product.setAvailableQuantity(product.getAvailableQuantity() - 1);
                }
            }

            return true;
        }, executorService);
    }

    private boolean isEmptyCart(Order order) {
        return order.getCart().isEmpty();
    }

    private Long getIdFromUser(String message) {
        System.out.println(message);
        Long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }
}
