package Services.Discount;

import java.math.BigDecimal;

public interface DiscountStrategy {
    // Metoda przyjmuje aktualną sumę i zwraca kwotę do odjęcia (rabat)
    // LUB zwraca nową cenę końcową. Tutaj przyjmijmy, że zwraca nową cenę.
    BigDecimal applyDiscount(BigDecimal totalAmount);
}