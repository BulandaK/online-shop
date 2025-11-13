import Console.ConsoleMenu;
import Manager.OrderManager;
import Manager.ProductManager;
import Models.Cart.Cart;
import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Product;
import Models.Product.ProductConfiguration;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ProductManager productManager = State.GlobalState.getProductManager();
        OrderManager orderManager = new OrderManager();


        Client kamilClient = new Client("Kamil", "Nowak", 1);
        Cart kamilCart = new Cart();
        Order kamilOrder = new Order(kamilClient, kamilCart);

        // tworzenie porduktow
        Product laptop = new Product(1L, "Laptop X", new BigDecimal("3000.00"), 5);
        ProductConfiguration laptopConfiguration = laptop.makeProductAsConfiguration(1L);

        Product mouse = new Product(2L, "Wireless Mouse", new BigDecimal("100.00"), 5);
        ProductConfiguration mouseConfiguration = mouse.makeProductAsConfiguration(2L);

        Product laptopWithMouse = new Product(3L, "Laptop X with Wireless Mouse", new BigDecimal("3100.00"), 5);

        laptopWithMouse.setConfigurations(List.of(mouseConfiguration, laptopConfiguration));
        mouseConfiguration.setConfiguredProducts(List.of(laptopWithMouse));
        laptopConfiguration.setConfiguredProducts(List.of(laptopWithMouse));
        //koniec tworzenia produktow

        productManager.addToInventory(laptop);
        productManager.addToInventory(mouse);
        productManager.addToInventory(laptopWithMouse);


        ConsoleMenu consoleMenu = new ConsoleMenu(orderManager, kamilOrder);
        consoleMenu.run();


    }

}