package Manager;

import Models.Cart.Cart;
import Models.Product.Product;
import MyException.EmptyCartException;
import MyException.InsufficientStockException;

import java.util.Optional;

/**
 * Manages operations related to the shopping cart.
 * Acts as a bridge between the User Interface and the Cart Model.
 */
public class CartManager {

    private final ProductManager productManager;

    public CartManager() {
        this.productManager = State.GlobalState.getProductManager();
    }

    /**
     * Adds a product to the cart by its ID.
     *
     * @param cart The cart to add the product to.
     * @param id   The unique identifier of the product.
     * @throws InsufficientStockException if the product is out of stock (checked during adding).
     */
    public void addByIdToCart(Cart cart, Long id) {
        Optional<Product> productToAdd = productManager.getProductById(id);

        productToAdd.ifPresentOrElse(
                product -> {
                    if (product.getAvailableQuantity() > 0
                            && alreadyInCart(product, cart) < product.getAvailableQuantity()
                    ) {
                        cart.addProduct(product);
                        System.out.println("Dodano produkt: " + product.getName());
                    } else {
                        throw new InsufficientStockException("Brak dostępnych sztuk dla produktu id=" + id);
                    }
                },
                () -> System.out.println("Nie można dodać do koszyka, taki produkt nie istnieje")
        );
    }

    /**
     * Removes a product from the cart by its ID.
     *
     * @param cart The cart to remove the product from.
     * @param id   The unique identifier of the product.
     * @return The optional of removed {@link Product} object.
     */
    public Optional<Product> removeByIdFromCart(Cart cart, Long id) {

        Optional<Product> removed = cart.getProducts().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();

        if (removed.isEmpty()) {
            System.out.println("Produkt o podanym ID nie znajduje się w koszyku.");
            return Optional.empty();
        }

        cart.removeProduct(removed.get());
        return removed;
    }

    /**
     * Displays the contents of the cart and the total price.
     *
     * @param cart The cart to display.
     */
    public void showCart(Cart cart) {
        System.out.println("koszyk uzytkownika:");
        for (Product product : cart.getProducts()) {
            System.out.println(product);
        }
        System.out.println("Suma: " + cart.sumPrices());
    }

    /**
     * Checks how many products are already in cart
     *
     * @param productInCart product that we are checking
     * @param cart          The cart in which we are counting products
     * @return number of how many pass products are already in cart
     */
    private int alreadyInCart(Product productInCart, Cart cart) {
        return (int) cart.getProducts().stream()
                .filter(product -> product.equals(productInCart))
                .count();

    }

    /**
     * Updates product quantities in the inventory based on the cart contents.
     * Typically called after a successful order.
     *
     * @param cart The cart to finalize.
     */
    public void finalizeCart(Cart cart) {
        cart.getProducts().forEach(product -> {
            product.setAvailableQuantity(product.getAvailableQuantity() - 1);
        });
        cart.clear();
    }
}


