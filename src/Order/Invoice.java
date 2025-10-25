package Order;

import Cart.Cart;

import java.time.LocalDate;

public class Invoice {
    private String number;
    private LocalDate issueDate;
    private Client client;
    private Cart cart;
    private double vatRate;

    public Invoice(String number, Client client, Cart cart, double vatRate) {
        this.number = number;
        this.issueDate = LocalDate.now();
        this.client = client;
        this.cart = cart;
        this.vatRate = vatRate;
    }

    public double getNetAmount() {
        return cart.sumPrices();
    }

    public double getVatAmount() {
        return getNetAmount() * vatRate;
    }

    public double getGrossAmount() {
        return getNetAmount() + getVatAmount();
    }

    public void showInvoice() {
        System.out.println("===================================");
        System.out.println("INVOICE no: " + number);
        System.out.println("Date: " + issueDate);
        System.out.println("Customer: " + client);
        System.out.println("-----------------------------------");
        cart.showCart();
        System.out.println("-----------------------------------");
        System.out.println("Net amount: " + getNetAmount());
        System.out.println("VAT (" + (vatRate * 100) + "%): " + getVatAmount());
        System.out.println("Total (gross): " + getGrossAmount());
        System.out.println("===================================");
    }
}
