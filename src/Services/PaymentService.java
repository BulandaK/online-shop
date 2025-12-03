package Services;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Simulates an external payment gateway service.
 * Used to demonstrate asynchronous processing delays.
 */
public class PaymentService {

    /**
     * Processes a payment for the given amount.
     * <p>
     * This method simulates a network delay of 2 seconds and has a random chance
     * of failure (simulating insufficient funds or bank errors).
     * </p>
     *
     * @param amount The total amount to be paid.
     * @return {@code true} if payment was successful, {@code false} otherwise.
     */
    public boolean processPayment(BigDecimal amount) {
        System.out.println(" [BANK] Rozpoczynam przetwarzanie płatności na kwotę: " + amount);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        boolean isSuccess = new Random().nextInt(10) != 0;

        if (isSuccess) {
            System.out.println(" [BANK] Płatność zaakceptowana!");
        } else {
            System.out.println(" [BANK] Błąd płatności - brak środków.");
        }

        return isSuccess;
    }
}