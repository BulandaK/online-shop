package Manager;

import Models.Product.Product;
import MyException.DuplicateProductException;
import MyException.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages the inventory of products available in the online shop.
 * <p>
 * This class provides methods to add, remove, update, and search for products.
 * It maintains the state of the inventory in memory.
 * </p>
 */
public class ProductManager {

    /**
     * The internal list holding all available products.
     */
    private final List<Product> inventory;

    /**
     * Initializes a new ProductManager with an empty inventory.
     */
    public ProductManager() {
        inventory = new ArrayList<>();
    }

    /**
     * Searches for a product by its unique identifier.
     *
     * @param id The ID of the product to search for.
     * @return An {@link Optional} containing the product if found, or empty if not.
     */
    public Optional<Product> getProductById(Long id) {
        return inventory.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param product The {@link Product} object to be added.
     * @throws DuplicateProductException if a product with the same ID already exists in the inventory.
     */
    public void addToInventory(Product product) throws DuplicateProductException {
        if (getProductById(product.getId()).isPresent()) {
            throw new DuplicateProductException("Produkt o id=" + product.getId() + " już istnieje");
        }
        inventory.add(product);
    }

    /**
     * Removes a product from the inventory based on its ID.
     * If the product does not exist, the operation is ignored.
     *
     * @param id The ID of the product to remove.
     */
    public void removeFromInventory(Long id) {
        inventory.removeIf(product -> product.getId().equals(id));
    }

    /**
     * Displays all products currently in the inventory to the console.
     * Delegates printing to the {@link Product#toString()} method.
     */
    public void showInventory() {
        inventory.forEach(System.out::println);
    }

    /**
     * Updates an existing product's details with new values.
     *
     * @param id             The ID of the product to update.
     * @param updatedProduct A {@link Product} object containing the new data (price, name, etc.).
     * @throws ProductNotFoundException if no product with the specified ID exists in the inventory.
     */
    public void toUpdate(Long id, Product updatedProduct) throws ProductNotFoundException {
        Optional<Product> searchedProduct = getProductById(id);

        if (searchedProduct.isEmpty()) {
            throw new ProductNotFoundException("Nie ma produktu, który chcesz updatowac");
        }

        // Updating fields
        searchedProduct.get().setPrice(updatedProduct.getPrice());
        searchedProduct.get().setName(updatedProduct.getName());
        searchedProduct.get().setConfigurations(updatedProduct.getConfigurations());
        searchedProduct.get().setAvailableQuantity(updatedProduct.getAvailableQuantity());
    }
}