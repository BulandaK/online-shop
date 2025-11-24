package Manager;

import Models.Product.Product;
import MyException.DuplicateProductException;
import MyException.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {
    private final List<Product> inventory;

    public ProductManager() {
        inventory = new ArrayList<Product>();
    }

    public Optional<Product> getProductById(Long id) {
        return inventory.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public void addToInventory(Product product) throws DuplicateProductException {
        if (getProductById(product.getId()).isPresent()) {
            throw new DuplicateProductException("Produkt o id=" + product.getId() + " już istnieje");
        }
        inventory.add(product);
    }

    public void removeFromInventory(Long id) {
        inventory.removeIf(product -> product.getId().equals(id));
    }

    public void showInventory() {
        for (Product product : inventory) {
            System.out.println(product);
        }
    }

    public void updateProduct(Long id, Product updatedProduct) throws ProductNotFoundException {
        Optional<Product> searchedProduct = getProductById(id);

        if (searchedProduct.isEmpty()) {
            throw new ProductNotFoundException("Nie ma produktu, który chcesz updatowac");
        }
        removeFromInventory(searchedProduct.get().getId());
        addToInventory(updatedProduct);
    }
}
