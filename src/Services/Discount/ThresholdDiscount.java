package Services.Discount;

import java.math.BigDecimal;

public class ThresholdDiscount implements DiscountStrategy {
    private final BigDecimal threshold;
    private final BigDecimal discountAmount;

    public ThresholdDiscount(String threshold, String discountAmount) {
        this.threshold = new BigDecimal(threshold);
        this.discountAmount = new BigDecimal(discountAmount);
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalAmount) {
        if (totalAmount.compareTo(threshold) > 0) {
            return totalAmount.subtract(discountAmount).max(BigDecimal.ZERO);
        }
        return totalAmount;
    }
}