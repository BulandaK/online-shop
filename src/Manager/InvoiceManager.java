package Manager;

import Models.Order.Invoice;
import Models.Order.Order;
import Services.InvoicePersistence;

import java.io.IOException;

/**
 * Manages the lifecycle and generation of invoices in the system.
 * <p>
 * This class acts as a facade for invoice operations, coordinating the creation
 * of the invoice object and its persistence to a file via {@link InvoicePersistence}.
 * </p>
 */
public class InvoiceManager {

    /**
     * Generates an invoice for a given order, displays it, and saves it to storage.
     * <p>
     * This method performs the following steps:
     * <ol>
     * <li>Creates a new {@link Invoice} instance with a unique number.</li>
     * <li>Displays the invoice details to the console.</li>
     * <li>Attempts to save the invoice to a text file ("invoices/invoices.txt").</li>
     * </ol>
     * </p>
     *
     * @param order The completed {@link Order} for which the invoice is to be generated.
     *              Must contain a valid client and a non-empty cart (or snapshot).
     */
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