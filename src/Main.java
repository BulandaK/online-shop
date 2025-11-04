import Console.ConsoleMenu;
import Manager.OrderManager;
import Manager.ProductManager;
import Models.Cart.Cart;
import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Computer;
import Models.Product.Smartphone;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        ProductManager productManager = new ProductManager();
        OrderManager orderManager = new OrderManager(productManager);


        Client kamilClient = new Client("Kamil","Nowak",1);
        Cart kamilCart = new Cart(productManager);
        Order kamilOrder = new Order(kamilClient,kamilCart);


        Computer kamilComputer = new Computer(1L,"Kamil Computer",new BigDecimal(2200),5,"i5",32);
        Smartphone randomSmartphone = new Smartphone(2L,"iphone",new BigDecimal(1500),5,"black",3000);

        productManager.addToInventory(kamilComputer);
        productManager.addToInventory(randomSmartphone);


        ConsoleMenu consoleMenu = new ConsoleMenu(orderManager,kamilOrder);
        consoleMenu.run();
    }

}