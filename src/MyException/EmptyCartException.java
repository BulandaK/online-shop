package MyException;

/**
 * Exception thrown when an operation cannot be performed because the shopping cart is empty.
 * <p>
 * Examples include trying to place an order or remove items when none are present.
 * </p>
 */
public class EmptyCartException extends RuntimeException {

    /**
     * Constructs a new EmptyCartException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public EmptyCartException(String message) {
        super(message);
    }
}