package Services;

/**
 * A generic interface for objects that require configuration.
 * <p>
 * This interface allows applying a configuration object of type {@code T} to the implementing class.
 * Useful for products with customizable features.
 * </p>
 *
 * @param <T> The type of the configuration item.
 */
public interface Configurable<T> {

    /**
     * Configures the object using the provided item.
     *
     * @param item The configuration item.
     */
    public void configure(T item);
}