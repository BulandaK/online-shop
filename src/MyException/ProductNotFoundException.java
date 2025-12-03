package MyException;

/**
 * Exception thrown when a requested product cannot be found in the inventory.
 * <p>
 * This often occurs during search, update, or removal operations where the provided
 * product ID does not correspond to any existing item.
 * </p>
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructs a new ProductNotFoundException with the specified detail message.
     *
     * @param message The detail message explaining which product could not be found.
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}