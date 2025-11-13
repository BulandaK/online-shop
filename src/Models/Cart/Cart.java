package Models.Cart;

import Manager.ProductManager;
import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private final List<Product> products;
    private final ProductManager productManager;

    public Cart() {
        products = new ArrayList<Product>();
        productManager = State.GlobalState.getProductManager();
    }

    public void addToCart(Long id) {

        Optional<Product> productToAdd = productManager.getProductById(id);

        productToAdd.ifPresentOrElse(
                prod -> {
                    if (prod.getAvailableQuantity() > 0) {
                        products.add(prod);
                    }
                },
                () -> System.out.println("Nie mozna dodac do koszyka, taki produkt nie istnieje")
        );

    }

    public Product removeFromCart(Long id) {
        Product removed = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (removed == null) {
            return null;
        }

        products.remove(removed);
        return removed;
    }

    public void showCart() {
        System.out.println("koszyk uzytkownika:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public Order makeOrder(Client client) {
        return new Order(client, this);
    }

    public BigDecimal sumPrices() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Product> getProducts() {
        return products;
    }
}
