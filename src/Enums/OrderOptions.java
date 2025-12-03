package Enums;

/**
 * Enumeration of available user actions in the console menu.
 */
public enum OrderOptions {

    /**
     * Show all available products in inventory.
     */
    SHOW_PRODUCTS("Wyświetl produkty"),

    /**
     * Add a selected product to the cart.
     */
    ADD_TO_CART("Dodaj do koszyka"),

    /**
     * Remove a selected product from the cart.
     */
    REMOVE_FROM_CART("Usuń z koszyka"),

    /**
     * Enter a discount code to apply reduction.
     */
    ENTER_DISCOUNT_CODE("Wpisz kod rabatowy"),

    /**
     * Submit the current order for processing.
     */
    MAKE_ORDER("Złóż zamówienie"),

    /**
     * Exit the application.
     */
    EXIT("WYjście");

    private final String description;

    OrderOptions(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}