package Console;

import Enums.OrderOptions;
import Manager.OrderManager;
import Models.Order.Order;

import java.util.Scanner;

/**
 * Handles the Command Line Interface (CLI) for the Online Shop application.
 * <p>
 * This class is responsible for displaying the menu options to the user,
 * reading user input, and delegating business logic execution to the {@link OrderManager}.
 * </p>
 */
public class ConsoleMenu {

    /**
     * The manager responsible for executing business logic (adding items, finalizing orders).
     */
    private final OrderManager orderManager;

    /**
     * The current order context for the session.
     */
    private final Order order;

    /**
     * Initializes the console menu with the necessary dependencies.
     *
     * @param orderManager The manager handling order logic and inventory.
     * @param order        The specific order instance being processed in this session.
     */
    public ConsoleMenu(OrderManager orderManager, Order order) {
        this.orderManager = orderManager;
        this.order = order;
    }

    /**
     * Starts the main application loop.
     * <p>
     * This method displays the welcome message and repeatedly shows the menu
     * until the user selects the {@link OrderOptions#EXIT} option.
     * It handles the mapping between menu options and {@link OrderManager} methods.
     * </p>
     */
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
                case EXIT -> orderManager.close(); // Ensures background threads are stopped
            }

        } while (option != OrderOptions.EXIT);
        System.out.println("Wyjście z programu");
    }

    /**
     * Displays all available menu options to the console.
     * <p>
     * Iterates through the {@link OrderOptions} enum to print the description of each action
     * along with its corresponding number.
     * </p>
     */
    private void showOptions() {
        int optionNumber = 1;
        for (OrderOptions option : OrderOptions.values()) {
            System.out.println(optionNumber + " - " + option.getDescription());
            optionNumber++;
        }
    }

    /**
     * Prompts the user for a numeric input and maps it to a valid {@link OrderOptions}.
     * <p>
     * This method includes input validation:
     * <ul>
     * <li>Catches non-integer inputs to prevent crashes.</li>
     * <li>Checks if the number is within the valid range of options.</li>
     * </ul>
     * It loops indefinitely until valid input is received.
     * </p>
     *
     * @return The selected {@link OrderOptions} enum value.
     */
    private OrderOptions getOptionFromUser() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= OrderOptions.values().length) {
                    return OrderOptions.values()[choice - 1];
                }
            } catch (Exception ignored) {
                // Catches NumberFormatException and any other scanner issues
            }

            System.out.println("Niepoprawny wybór, spróbuj ponownie.");
        }
    }

    /**
     * Prints a personalized welcome message using the client's name from the current order.
     */
    private void helloUser() {
        System.out.println("witaj uzytkowniku: " + this.order.getClient().getName());
    }
}