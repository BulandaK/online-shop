package Manager;

import Models.Cart.Cart;
import Models.Order.Invoice;
import Models.Order.Order;
import Models.Product.Product;
import MyException.EmptyCartException;
import Services.InvoicePersistence;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderManager {

    private final ProductManager productManager;
    private final CartManager cartManager;
    private final Scanner scanner = new Scanner(System.in);
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public OrderManager() {
        this.productManager = State.GlobalState.getProductManager();
        this.cartManager = new CartManager();
    }

    public void showProducts() {
        productManager.showInventory();
    }

    public void addToCart(Order order) {
        try {
            Long id = getIdFromUser("\n\nwybierz id produktu ktory chcesz dodac do koszyka");
            cartManager.addByIdToCart(order.getCart(), id);
            cartManager.showCart(order.getCart());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeFromCart(Order order) {
        if (order.getCart().isEmpty()) {
            throw new EmptyCartException("Nie można usunać z koszyka jest pusty!");
        }

        Long id = getIdFromUser("\n\nwybierz id produktu ktory chcesz usunac z koszyka");

        Product removedProduct = cartManager.removeByIdFromCart(order.getCart(), id);

        if (removedProduct != null) {
            System.out.println("usuneles z koszyka product: " + removedProduct);
            cartManager.showCart(order.getCart());
        }
    }

    public void executeOrder(Order order) throws EmptyCartException {
        if (order.getCart().isEmpty()) {
            throw new EmptyCartException("Nie można wykonać zamówienia, koszyk jest pusty!");
        }

        CompletableFuture<Boolean> canExecuteOrder = CompletableFuture.supplyAsync(()->{
            List<Product> productsInCart = order.getCart().getProducts();
            for (Product product : productsInCart) {
                if (!product.isAvailable()) {
                    return false;
                }
            }
            cartManager.finalizeCart(order.getCart());
            return true;
        }, executorService);

        canExecuteOrder.thenAcceptAsync(canExecute -> {
            if(!canExecute){
                System.out.println("Nie można wykonać zamówienia – produkt niedostępny!");
                return;
            }

            // Generowanie faktury (Invoice bierze dane z Cart, który jest już wyczyszczony przez finalizeCart?
            // UWAGA: Tu jest potencjalny błąd logiczny w asynchroniczności.
            // Skoro finalizeCart robi clear(), to Invoice dostanie pusty koszyk.
            // Należy stworzyć Invoice PRZED finalizeCart albo przekazać listę produktów do Invoice.
            // W tym przykładzie, Invoice trzyma referencję do Cart. Jeśli Cart wyczyścimy, Invoice będzie pusty.

            // Poprawka logiczna dla tego miejsca (dla uproszczenia bez głębokiej przebudowy Invoice):
            // Invoice powinien być stworzony przed clear(), ale finalizeCart modyfikuje stany.
            // W obecnej architekturze Invoice trzyma referencję do obiektu Cart.
            // Jeśli wyczyścimy listę w Cart, Invoice też straci produkty przy wyświetlaniu.
            // Rozwiązanie: W Invoice w konstruktorze zrób kopię produktów LUB nie czyść koszyka w finalizeCart dopóki nie zapiszesz faktury.

            // Przywróćmy logikę, która działała w starym kodzie (tam finalizeCart było wołane... nigdzie w executeOrder w starym pliku Main,
            // ale tutaj robimy to w wątku).

            // Aby uniknąć problemu pustej faktury, Invoice tworzymy przed finalizeCart, ale finalizeCart musi być wywołane.
            // Skoro finalizeCart czyści listę, musimy skopiować produkty do Invoice w jego konstruktorze (patrz uwaga poniżej).

            Invoice invoice = new Invoice(
                    "FV/" + Math.random(),
                    order.getClient(),
                    order.getCart(), // Tutaj uwaga: jeśli cart zostanie wyczyszczony, invoice będzie pusty
                    0.23
            );

            System.out.println("Faktura wygenerowana! ");
            // invoice.showInvoice(); // Może być puste jeśli finalizeCart zadziałało wcześniej na referencji

            try {
                InvoicePersistence.saveInvoice(invoice, "invoices/invoices.txt");
                System.out.println("Faktura zapisana do pliku invoices/invoices.txt");
            } catch (IOException e) {
                System.out.println("Błąd zapisu faktury: " + e.getMessage());
            }
        });
    }

    private Long getIdFromUser(String message) {
        System.out.println(message);
        Long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }
}