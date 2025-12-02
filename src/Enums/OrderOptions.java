package Enums;

public enum OrderOptions {
    SHOW_PRODUCTS("Wyświetl produkty"), ADD_TO_CART("Dodaj do koszyka"), REMOVE_FROM_CART("Usuń z koszyka"), MAKE_ORDER("Złóż zamówienie"), EXIT("WYjście");

    private final String description;

    OrderOptions(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
