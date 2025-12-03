package Services.Discount;

import java.math.BigDecimal;

/**
 * A discount strategy that applies a fixed amount reduction if the total price exceeds a certain threshold.
 * <p>
 * Example: "Subtract 50 PLN if total is greater than 1000 PLN".
 * </p>
 */
public class ThresholdDiscount implements DiscountStrategy {
    private final BigDecimal threshold;
    private final BigDecimal discountAmount;

    /**
     * Constructs a new ThresholdDiscount.
     *
     * @param threshold      The minimum total price required to trigger the discount.
     * @param discountAmount The fixed amount to be deducted if the threshold is met.
     */
    public ThresholdDiscount(String threshold, String discountAmount) {
        this.threshold = new BigDecimal(threshold);
        this.discountAmount = new BigDecimal(discountAmount);
    }

    /**
     * Applies the discount if the total amount exceeds the threshold.
     *
     * @param totalAmount The original price.
     * @return The price minus the discount amount if the threshold is met; otherwise, the original price.
     * The result is never less than zero.
     */
    @Override
    public BigDecimal applyDiscount(BigDecimal totalAmount) {
        if (totalAmount.compareTo(threshold) > 0) {
            return totalAmount.subtract(discountAmount).max(BigDecimal.ZERO);
        }
        return totalAmount;
    }
}