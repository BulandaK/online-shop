package MyException;

/**
 * Exception thrown when an error occurs during the inventory update process.
 * <p>
 * This serves as a generic runtime exception for unexpected issues while modifying
 * product states or quantities in the {@link Manager.ProductManager}.
 * </p>
 */
public class InventoryUpdateException extends RuntimeException {

    /**
     * Constructs a new InventoryUpdateException with the specified detail message.
     *
     * @param message The detail message describing the update error.
     */
    public InventoryUpdateException(String message) {
        super(message);
    }
}