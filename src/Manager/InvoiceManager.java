package Manager;

import Models.Order.Invoice;
import Models.Order.Order;
import Services.InvoicePersistence;

import java.io.IOException;

public class InvoiceManager {
    public static void generateInvoice(Order order) {
        Invoice invoice = new Invoice(
                "FV/" + Math.random(),
                order.getClient(),
                order.getCart(),
                0.23
        );

        System.out.println("Faktura wygenerowana! ");
        invoice.showInvoice();


        try {
            InvoicePersistence.saveInvoice(invoice, "invoices/invoices.txt");
            System.out.println("Faktura zapisana do pliku invoices/invoices.txt");
        } catch (IOException e) {
            System.out.println("Błąd zapisu faktury: " + e.getMessage());
        }
    }
}
