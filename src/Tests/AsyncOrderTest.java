package Tests;

import Manager.CartManager;
import Manager.OrderManager;
import Manager.ProductManager;
import Models.Cart.Cart;
import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Product;
import State.GlobalState;

import java.math.BigDecimal;

public class AsyncOrderTest {
    public static void main(String[] args) throws InterruptedException {
        ProductManager pm = GlobalState.getProductManager();
        Product laptop = new Product(1L, "Laptop Gaming", new BigDecimal("5000"), 5);
        pm.addToInventory(laptop);

        OrderManager orderManager = new OrderManager();
        CartManager cartManager = new CartManager();

        Client client = new Client("Tester", "Asynchroniczny", 1L);
        Cart cart = new Cart();
        Order order = new Order(client, cart);

        cartManager.addByIdToCart(cart, 1L);

        System.out.println("1. Klient klika 'ZAMÓW'");

        orderManager.executeOrder(order);

        System.out.println("2. UI jest odblokowane! Możesz przeglądać inne produkty...");
        for (int i = 0; i < 5; i++) {
            Thread.sleep(600);
            System.out.println("   (UI) Użytkownik przegląda ofertę... " + (i + 1));
        }

        // Czekamy chwilę, żeby wątek tła zdążył wypisać komunikaty zanim program się zamknie
        Thread.sleep(2000);
        System.out.println("\n--- Koniec testu ---");
    }
}