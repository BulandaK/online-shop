package Models.Order;

import Models.Cart.Cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {
    private String number;
    private LocalDateTime issueDate;
    private Client client;
    private Cart cart;
    private double vatRate;

    public Invoice(String number, Client client, Cart cart, double vatRate) {
        this.number = number;
        this.issueDate = LocalDateTime.now();
        this.client = client;
        this.cart = cart;
        this.vatRate = vatRate;
    }

    public BigDecimal getNetAmount() {
        return cart.sumPrices();
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

    public Cart getCart() {
        return cart;
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
        cart.show();
        System.out.println("-----------------------------------");
        System.out.println("Net amount: " + getNetAmount());
        System.out.println("VAT (" + (vatRate * 100) + "%): " + getVatAmount());
        System.out.println("Total (gross): " + getGrossAmount());
        System.out.println("===================================");
    }
}
