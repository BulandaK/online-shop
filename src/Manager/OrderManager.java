package Manager;

import Models.Cart.Cart;
import Models.Order.Invoice;
import Models.Order.Order;
import Models.Product.Product;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OrderManager {

    private final ProductManager productManager;
    private final Scanner scanner = new Scanner(System.in);

    public OrderManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public void showProducts() {
        productManager.showInventory();
    }

    public void addToCart(Order order) {
        Long id = getIdFromUser("\n\nwybierz id produktu ktory chcesz dodac do koszyka");

        order.getCart().addToCart(id);
        order.getCart().showCart();
    }

    public void removeFromCart(Order order) {
        Long id = getIdFromUser("\n\nwybierz id produktu ktory chcesz usunac z koszyka");

        Product removedProduct = order.getCart().removeFromCart(id);
        if (removedProduct != null) {
            System.out.println("usuneles z koszyka product: " + removedProduct);
            order.getCart().showCart();
        }

    }

    public void makeOrder(Order order) {
        System.out.println("robie order");


        Invoice invoice = new Invoice(
                "FV/" + Math.random(),
                order.getClient(),
                order.getCart(),
                0.23
        );

        System.out.println("Faktura wygenerowana! ");
        invoice.showInvoice();
    }

    private Long getIdFromUser(String message) {
        System.out.println(message);

        Long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }
}
