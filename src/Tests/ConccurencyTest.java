package Tests;

import Manager.OrderManager;
import Manager.ProductManager;
import Models.Cart.Cart;
import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Product;

import java.math.BigDecimal;

public class ConccurencyTest {

    public static void main(String[] args) throws InterruptedException {
        ProductManager pm = State.GlobalState.getProductManager();

        Product mouse = new Product(1L, "Mouse", new BigDecimal("50"), 2);
        pm.addToInventory(mouse);

        OrderManager orderManager = new OrderManager();

        System.out.println("Dostępność: " + mouse.getAvailableQuantity());

        Cart cart1 = new Cart();
        cart1.add(1L);
        Client client1 = new Client("Jan", "jan@test.com", 1L);
        Order order1 = new Order(client1, cart1);

        Cart cart2 = new Cart();
        cart2.add(1L);
        Client client2 = new Client("Anna", "anna@test.com", 2L);
        Order order2 = new Order(client2, cart2);

        Cart cart3 = new Cart();
        cart3.add(1L);
        Client client3 = new Client("Piotr", "piotr@test.com", 3L);
        Order order3 = new Order(client3, cart3);

        System.out.println("\n=== Wykonuję 3 zamówienia jednocześnie ===\n");

        orderManager.executeOrder(order1);
        orderManager.executeOrder(order2);
        orderManager.executeOrder(order3);

        // Czekaj na zakończenie
        Thread.sleep(2000);

        System.out.println("\n=== Wynik ===");
        System.out.println("Dostępność po zamówieniach: " + mouse.getAvailableQuantity());
        System.out.println("\nSprawdź invoices/invoices.txt - powinny być 2 faktury");
    }
}