package Tests;

import Models.Cart.Cart;
import Models.Product.Product;
import Services.Discount.DiscountStrategy;
import Services.Discount.PercentageDiscount;
import Services.Discount.ThresholdDiscount;
import State.GlobalState;

import java.math.BigDecimal;

public class DiscountTest {

    public static void main(String[] args) {
        System.out.println("=== TESTOWANIE TASK 12: PROMOCJE I RABATY ===\n");

        setupInventory();
        Cart cart = new Cart();

        // Dodajemy Laptopa (id=1, cena=3000) i Myszkę (id=2, cena=100)
        // Razem: 3100.00
        System.out.println("--- Scenariusz 1: Koszyk bez zniżek ---");
        try {
            cart.add(1L);
            cart.add(2L);
        } catch (Exception e) {
            System.out.println("Błąd dodawania do koszyka: " + e.getMessage());
        }

        BigDecimal basePrice = cart.sumPrices();
        System.out.println("Cena bazowa (oczekiwana: 3100.00): " + basePrice);
        printResult(basePrice.compareTo(new BigDecimal("3100.00")) == 0);


        // 3. Test zniżki procentowej (np. -20%)
        System.out.println("\n--- Scenariusz 2: Zniżka procentowa 20% ---");
        DiscountStrategy twentyPercent = new PercentageDiscount(0.20);
        cart.setDiscountStrategy(twentyPercent);

        BigDecimal priceWithPercentage = cart.sumPrices();
        // 3100 * 0.8 = 2480.00
        System.out.println("Cena po rabacie 20% (oczekiwana: 2480.00): " + priceWithPercentage);
        printResult(priceWithPercentage.compareTo(new BigDecimal("2480.00")) == 0);


        // 4. Test zniżki kwotowej (np. -50 PLN przy zakupach powyżej 1000 PLN)
        System.out.println("\n--- Scenariusz 3: Zniżka kwotowa (-50 PLN dla > 1000 PLN) ---");
        // Warunek spełniony, bo mamy 3100 PLN w koszyku
        DiscountStrategy thresholdDiscount = new ThresholdDiscount("1000.00", "50.00");
        cart.setDiscountStrategy(thresholdDiscount);

        BigDecimal priceWithThreshold = cart.sumPrices();
        // 3100 - 50 = 3050.00
        System.out.println("Cena po rabacie kwotowym (oczekiwana: 3050.00): " + priceWithThreshold);
        printResult(priceWithThreshold.compareTo(new BigDecimal("3050.00")) == 0);


        // 5. Test zniżki kwotowej (warunek niespełniony)
        System.out.println("\n--- Scenariusz 4: Zniżka kwotowa (Warunek niespełniony) ---");
        // Ustawiamy wysoki próg: -50 PLN tylko powyżej 5000 PLN
        DiscountStrategy highThresholdDiscount = new ThresholdDiscount("5000.00", "50.00");
        cart.setDiscountStrategy(highThresholdDiscount);

        BigDecimal priceConditionNotMet = cart.sumPrices();
        // Warunek niespełniony (3100 < 5000), cena powinna zostać bazowa (3100.00)
        System.out.println("Cena przy niespełnionym warunku (oczekiwana: 3100.00): " + priceConditionNotMet);
        printResult(priceConditionNotMet.compareTo(new BigDecimal("3100.00")) == 0);
    }

    private static void setupInventory() {
        Product laptop = new Product(1L, "Test Laptop", new BigDecimal("3000.00"), 10);
        Product mouse = new Product(2L, "Test Mouse", new BigDecimal("100.00"), 10);

        GlobalState.getProductManager().addToInventory(laptop);
        GlobalState.getProductManager().addToInventory(mouse);
    }

    // Prosta metoda do wyświetlania wyniku testu
    private static void printResult(boolean passed) {
        if (passed) {
            System.out.println("TEST ZALICZONY");
        } else {
            System.out.println("TEST NIEZALICZONY");
        }
    }
}