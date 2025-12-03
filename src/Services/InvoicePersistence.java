package Services;

import Models.Order.Invoice;
import Models.Product.Product;

import java.io.IOException;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;

/**
 * Service responsible for persisting invoice data to the file system.
 * <p>
 * This class handles the formatting and writing of {@link Invoice} objects to a specified file path.
 * It ensures that the necessary directories exist before writing.
 * </p>
 */
public class InvoicePersistence {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Appends the details of an invoice to a text file.
     * <p>
     * If the file or its parent directories do not exist, they are created.
     * The invoice is formatted with a header, product list, and price summary.
     * This method is synchronized to prevent race conditions when multiple threads write to the same file.
     * </p>
     *
     * @param invoice  The {@link Invoice} object to be saved.
     * @param filePath The path to the file where the invoice should be written.
     * @throws IOException If an I/O error occurs writing to or creating the file.
     */
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

        for (Product p : invoice.getProducts()) {
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