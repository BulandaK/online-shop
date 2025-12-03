package Services;

import java.math.BigDecimal;
import java.util.Random;

public class PaymentService {

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