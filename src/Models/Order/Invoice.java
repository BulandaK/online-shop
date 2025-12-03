package Models.Order;

import Models.Cart.Cart;
import Models.Product.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a final invoice generated after a successful order.
 * <p>
 * This class is immutable regarding the list of products and prices once created.
 * It creates a snapshot of the cart contents to preserve the state of the order
 * even if the cart is later cleared or modified.
 * </p>
 */
public class Invoice {
    private String number;
    private LocalDateTime issueDate;
    private Client client;

    /**
     * A copy of the products from the cart at the moment of invoice generation.
     */
    private List<Product> productsSnapshot;

    /**
     * The fixed net amount calculated at the moment of invoice generation.
     */
    private BigDecimal netAmount;

    private double vatRate;

    /**
     * Constructs a new Invoice.
     *
     * @param number  The unique invoice number.
     * @param client  The client associated with the invoice.
     * @param cart    The cart containing products to be billed. A snapshot of products is taken.
     * @param vatRate The VAT rate (e.g., 0.23 for 23%).
     */
    public Invoice(String number, Client client, Cart cart, double vatRate) {
        this.number = number;
        this.issueDate = LocalDateTime.now();
        this.client = client;
        this.vatRate = vatRate;
        // Create a snapshot to protect against cart clearing
        this.productsSnapshot = new ArrayList<>(cart.getProducts());
        // Freeze the price
        this.netAmount = cart.sumPrices();
    }

    /**
     * Gets the net amount (before tax).
     *
     * @return The net amount as BigDecimal.
     */
    public BigDecimal getNetAmount() {
        return this.netAmount;
    }

    /**
     * Calculates the VAT amount based on the net amount and VAT rate.
     *
     * @return The calculated VAT amount.
     */
    public double getVatAmount() {
        return getNetAmount().doubleValue() * vatRate;
    }

    /**
     * Calculates the gross amount (Net + VAT).
     *
     * @return The total gross amount.
     */
    public double getGrossAmount() {
        return getNetAmount().doubleValue() + getVatAmount();
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Returns the list of products included in this invoice.
     *
     * @return A list of {@link Product} objects.
     */
    public List<Product> getProducts() {
        return productsSnapshot;
    }

    public double getVatRate() {
        return vatRate;
    }

    /**
     * Prints the details of the invoice to the standard output.
     */
    public void showInvoice() {
        System.out.println("===================================");
        System.out.println("INVOICE no: " + number);
        System.out.println("Date: " + issueDate);
        System.out.println("Customer: " + client);
        System.out.println("-----------------------------------");

        System.out.println("Pozycje na fakturze:");
        for (Product product : productsSnapshot) {
            System.out.println(product);
        }

        System.out.println("-----------------------------------");
        System.out.println("Net amount: " + getNetAmount());
        System.out.println("VAT (" + (vatRate * 100) + "%): " + getVatAmount());
        System.out.println("Total (gross): " + getGrossAmount());
        System.out.println("===================================");
    }
}