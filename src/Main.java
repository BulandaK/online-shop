import Console.ConsoleMenu;
import Manager.OrderManager;
import Manager.ProductManager;
import Models.Cart.Cart;
import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Computer;
import Models.Product.Product;
import Models.Product.ProductConfiguration;
import Models.Product.Smartphone;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ProductManager productManager = new ProductManager();
        OrderManager orderManager = new OrderManager(productManager);


        Client kamilClient = new Client("Kamil", "Nowak", 1);
        Cart kamilCart = new Cart(productManager);
        Order kamilOrder = new Order(kamilClient, kamilCart);

        Product laptop = new Product(1L, "Laptop X", new BigDecimal("3000.00"), 5);
        ProductConfiguration laptopConfiguration = new ProductConfiguration(1L, "Laptop X", new BigDecimal("3000.00"), laptop);

        Product mouse = new Product(2L, "Wireless Mouse", new BigDecimal("100.00"), 5);
        ProductConfiguration mouseConfiguration = new ProductConfiguration(2L, "wireles mouse", new BigDecimal("100.00"), mouse);

        Product laptopWithMouse = new Product(3L, "Laptop X with Wireless Mouse", new BigDecimal("3100.00"), 5);

        laptopWithMouse.setConfigurations(List.of(mouseConfiguration, laptopConfiguration));
        mouseConfiguration.setConfiguredProducts(List.of(laptopWithMouse));

        productManager.addToInventory(laptop);
        productManager.addToInventory(mouse);
        productManager.addToInventory(laptopWithMouse);


        ConsoleMenu consoleMenu = new ConsoleMenu(orderManager, kamilOrder);
        consoleMenu.run();


    }

}