package Models.Order;

import Models.Cart.Cart;
import Models.Product.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String number;
    private LocalDateTime issueDate;
    private Client client;
    private List<Product> productsSnapshot;
    private BigDecimal netAmount;

    private double vatRate;

    public Invoice(String number, Client client, Cart cart, double vatRate) {
        this.number = number;
        this.issueDate = LocalDateTime.now();
        this.client = client;
        this.vatRate = vatRate;
        this.productsSnapshot = new ArrayList<>(cart.getProducts());
        this.netAmount = cart.sumPrices();
    }

    public BigDecimal getNetAmount() {
        return this.netAmount;
    }

    public double getVatAmount() {
        return getNetAmount().doubleValue() * vatRate;
    }

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

    public List<Product> getProducts() {
        return productsSnapshot;
    }

    public double getVatRate() {
        return vatRate;
    }

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