package Manager;

import Models.Cart.Cart;
import Models.Product.Product;
import MyException.InsufficientStockException;

import java.util.Optional;

public class CartManager {

    private final ProductManager productManager;

    public CartManager() {
        this.productManager = State.GlobalState.getProductManager();
    }

    public void addByIdToCart(Cart cart, Long id) {
        Optional<Product> productToAdd = productManager.getProductById(id);

        productToAdd.ifPresentOrElse(
                product -> {
                    if (product.getAvailableQuantity() > 0) {
                        cart.addProduct(product);
                        System.out.println("Dodano produkt: " + product.getName());
                    } else {
                        throw new InsufficientStockException("Brak dostępnych sztuk dla produktu id=" + id);
                    }
                },
                () -> System.out.println("Nie można dodać do koszyka, taki produkt nie istnieje")
        );
    }

    public Product removeByIdFromCart(Cart cart, Long id) {
        Product removed = cart.getProducts().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (removed == null) {
            System.out.println("Produkt o podanym ID nie znajduje się w koszyku.");
            return null;
        }

        cart.removeProduct(removed);
        return removed;
    }

    public void showCart(Cart cart) {
        System.out.println("koszyk uzytkownika:");
        for (Product product : cart.getProducts()) {
            System.out.println(product);
        }
        System.out.println("Suma: " + cart.sumPrices());
    }


    public void finalizeCart(Cart cart) {
        cart.getProducts().forEach(product -> {
            product.setAvailableQuantity(product.getAvailableQuantity() - 1);
        });
        cart.clear();
    }
}