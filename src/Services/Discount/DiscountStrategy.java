package Services.Discount;

import java.math.BigDecimal;

/**
 * Defines the contract for discount calculation strategies.
 * Implementation of the Strategy Design Pattern.
 */
public interface DiscountStrategy {

    /**
     * Applies a discount logic to the given total amount.
     *
     * @param totalAmount The original total price of the cart.
     * @return The new price after applying the discount.
     */
    BigDecimal applyDiscount(BigDecimal totalAmount);
}