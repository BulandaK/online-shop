package MyException;

/**
 * Exception thrown when a requested product quantity exceeds the available stock.
 * <p>
 * This ensures that users cannot buy more items than are physically available in the inventory.
 * </p>
 */
public class InsufficientStockException extends RuntimeException {

    /**
     * Constructs a new InsufficientStockException with the specified detail message.
     *
     * @param message The detail message, typically including the product ID or name.
     */
    public InsufficientStockException(String message) {
        super(message);
    }
}