package Console;

import Enums.OrderOptions;
import Manager.OrderManager;
import Models.Order.Order;

import java.util.Scanner;

public class ConsoleMenu {

    private final OrderManager orderManager;
    private final Order order;

    public ConsoleMenu(OrderManager orderManager, Order order) {
        this.orderManager = orderManager;
        this.order = order;
    }

    public void run() {
        helloUser();

        OrderOptions option;
        do {
            showOptions();
            option = getOptionFromUser();

            switch (option) {
                case SHOW_PRODUCTS -> orderManager.showProducts();
                case ADD_TO_CART -> orderManager.addToCart(order);
                case REMOVE_FROM_CART -> orderManager.removeFromCart(order);
                case MAKE_ORDER -> orderManager.executeOrder(order);
                case EXIT -> orderManager.close();
            }

        } while (option != OrderOptions.EXIT);
        System.out.println("Wyjście z programu");
    }

    private void showOptions() {
        int optionNumber = 1;
        for (OrderOptions option : OrderOptions.values()) {
            System.out.println(optionNumber + " - " + option.getDescription());
            optionNumber++;
        }
    }

    private OrderOptions getOptionFromUser() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= OrderOptions.values().length) {
                    return OrderOptions.values()[choice - 1];
                }
            } catch (Exception ignored) {
            }

            System.out.println("Niepoprawny wybór, spróbuj ponownie.");
        }
    }

    private void helloUser() {
        System.out.println("witaj uzytkowniku: " + this.order.getClient().getName());
    }
}
