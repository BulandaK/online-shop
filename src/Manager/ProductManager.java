package Manager;

import Models.Product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {
    private final List<Product> inventory;

    public ProductManager() {
        inventory = new ArrayList<Product>();
    }

    public Optional<Product> getProductById(Long id) {
        return inventory.stream().filter(product -> product.getId() == id).findFirst();
    }

    public void addToInventory(Product product) {
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

    public void updateProduct(int id, Product updatedProduct) {
        Optional<Product> searchedProduct = inventory.stream().filter(product -> product.getId() == id).findFirst();

        if (searchedProduct.isEmpty()) {
            System.out.println("nie ma wybranego produktu");
        }
        removeFromInventory(searchedProduct.get().getId());
        addToInventory(updatedProduct);
    }
}
