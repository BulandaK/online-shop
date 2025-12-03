import Console.ConsoleMenu;
import Manager.OrderManager;
import Manager.ProductManager;
import Models.Cart.Cart;
import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Product;
import Models.Product.ProductConfiguration;
import Services.Discount.PercentageDiscount;
import Services.Discount.ThresholdDiscount;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ProductManager productManager = State.GlobalState.getProductManager();
        OrderManager orderManager = new OrderManager();

        // === DEFINIOWANIE RABATÓW ===
        // 1. Rabat procentowy (np. 15% na hasło LATO15)
        orderManager.registerDiscount("LATO15", new PercentageDiscount(0.15));

        // 2. Rabat kwotowy (np. -50 PLN przy zakupach powyżej 200 PLN na hasło ZIMA50)
        orderManager.registerDiscount("ZIMA50", new ThresholdDiscount("200.00", "50.00"));

        // 3. Rabat VIP (np. 50% zniżki)
        orderManager.registerDiscount("VIPSECRET", new PercentageDiscount(0.50));
        // ============================

        Client kamilClient = new Client("Kamil", "Nowak", 1L);
        Cart kamilCart = new Cart();
        Order kamilOrder = new Order(kamilClient, kamilCart);

        // tworzenie porduktow
        Product laptop = new Product(1L, "Laptop X", new BigDecimal("3000.00"), 2);
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