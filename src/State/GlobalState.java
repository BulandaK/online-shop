package State;

import Manager.ProductManager;

/**
 * Maintains the global state of the application.
 * <p>
 * This class acts as a central point of access for shared resources, primarily implementing
 * the Singleton pattern for {@link ProductManager}. This ensures that all parts of the application
 * (orders, carts, tests) operate on the same inventory data.
 * </p>
 */
public class GlobalState {

    /**
     * The single shared instance of the ProductManager.
     */
    private static ProductManager productManager;

    /**
     * Retrieves the singleton instance of {@link ProductManager}.
     * <p>
     * Implements lazy initialization: the manager is created only when requested for the first time.
     * Subsequent calls return the same existing instance.
     * </p>
     *
     * @return The global {@link ProductManager} instance.
     */
    public static ProductManager getProductManager() {
        if (productManager == null) {
            productManager = new ProductManager();
        }
        return productManager;
    }
}