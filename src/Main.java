import Cart.Cart;
import Manager.ProductManager;
import Order.Invoice;
import Order.Order;
import Order.OrderProcessor;
import Order.Client;
import Product.Computer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();

        Client kamil = new Client("Kamil","Bulanda",1);
        Cart kamilCart = new Cart();


        for (int i = 0; i < 10; i++) {
            Computer computer =Computer.createRandomComputer(i);
            manager.addToInventory(computer);
            kamilCart.addToCart(computer);
        }

        kamilCart.showCart();


        Order kamilOrder = kamilCart.makeOrder(kamil);

        OrderProcessor myOrderProcessor = new OrderProcessor();
        Invoice invoiceForKamilOrder = myOrderProcessor.processOrder(kamilOrder);

        invoiceForKamilOrder.showInvoice();

    }

}