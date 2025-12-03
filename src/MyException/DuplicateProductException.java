package MyException;

/**
 * Exception thrown when attempting to add a product that already exists in the inventory.
 * <p>
 * This typically happens when trying to add a new product with an ID that is already
 * assigned to another product.
 * </p>
 */
public class DuplicateProductException extends RuntimeException {

    /**
     * Constructs a new DuplicateProductException with the specified detail message.
     *
     * @param message The detail message explaining which product caused the duplication.
     */
    public DuplicateProductException(String message) {
        super(message);
    }
}