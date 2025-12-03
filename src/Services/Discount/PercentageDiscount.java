package Services.Discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A discount strategy that applies a fixed percentage reduction to the total price.
 * <p>
 * Example: A 20% discount on 100.00 results in 80.00.
 * </p>
 */
public class PercentageDiscount implements DiscountStrategy {
    private final BigDecimal percentage;

    /**
     * Constructs a new PercentageDiscount.
     *
     * @param percentageValue The discount percentage as a decimal fraction (e.g., 0.20 for 20%).
     * @throws IllegalArgumentException if the value is not between 0.0 and 1.0.
     */
    public PercentageDiscount(double percentageValue) {
        if (percentageValue < 0 || percentageValue > 1) {
            throw new IllegalArgumentException("Procent musi być w zakresie 0.0 - 1.0");
        }
        this.percentage = BigDecimal.valueOf(percentageValue);
    }

    /**
     * Applies the percentage discount to the total amount.
     *
     * @param totalAmount The original price.
     * @return The price reduced by the configured percentage, rounded to 2 decimal places (HALF_UP).
     * Returns 0 if the input amount is negative or zero.
     */
    @Override
    public BigDecimal applyDiscount(BigDecimal totalAmount) {
        if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal discountAmount = totalAmount.multiply(percentage);
        return totalAmount.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }
}