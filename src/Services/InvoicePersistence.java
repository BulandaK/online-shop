package Services;

import Models.Cart.Cart;
import Models.Order.Invoice;
import Models.Product.Product;

import java.io.IOException;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;

public class InvoicePersistence {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static synchronized void saveInvoice(Invoice invoice, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===================================\n");
        sb.append("INVOICE no: ").append(invoice.getNumber()).append("\n");
        sb.append("Date: ").append(invoice.getIssueDate().format(DATE_FORMAT)).append("\n");
        sb.append("Customer: ").append(invoice.getClient()).append("\n");
        sb.append("-----------------------------------\n");

        Cart cart = invoice.getCart();
        for (Product p : cart.getProducts()) {
            sb.append(p).append("\n");
        }

        sb.append("-----------------------------------\n");
        sb.append("Net amount: ").append(invoice.getNetAmount()).append("\n");
        sb.append("VAT (").append(invoice.getVatRate() * 100).append("%): ").append(invoice.getVatAmount()).append("\n");
        sb.append("Total (gross): ").append(invoice.getGrossAmount()).append("\n");
        sb.append("===================================\n\n");

        Files.writeString(path, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
