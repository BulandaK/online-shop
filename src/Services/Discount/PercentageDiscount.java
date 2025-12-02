package Services.Discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageDiscount implements DiscountStrategy {
    private final BigDecimal percentage;

    // np. 0.10 dla 10%
    public PercentageDiscount(double percentageValue) {
        if (percentageValue < 0 || percentageValue > 1) {
            throw new IllegalArgumentException("Procent musi być w zakresie 0.0 - 1.0");
        }
        this.percentage = BigDecimal.valueOf(percentageValue);
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal totalAmount) {
        if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal discountAmount = totalAmount.multiply(percentage);
        return totalAmount.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }
}