package Manager;

import Product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {
    private final List<Product> inventory;

    public ProductManager(){
        inventory = new ArrayList<Product>();
    }
    public void addToInventory(Product product) {
        inventory.add(product);
    }

    public void removeFromInventory(int id) {
        inventory.removeIf(p -> p.getId() == id);
    }

    public void showInventory() {
        for (Product prod : inventory) {
            System.out.println(prod);
        }
    }

    public void updateProduct(int id, Product updatedProduct) {
        Optional<Product> searchedProduct =inventory.stream().filter(p->p.getId()==id).findFirst();

        if(searchedProduct.isPresent()){
            removeFromInventory(searchedProduct.get().getId());
            addToInventory(updatedProduct);
        }else {
            System.out.println("nie ma wybranego produktu");
        }
    }
}
