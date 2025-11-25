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

    public void add(Long id) {

            Optional<Product> productToAdd = productManager.getProductById(id);
        
        productToAdd.ifPresentOrElse(
                product -> {
                    if (product.getAvailableQuantity() > 0) {
                        products.add(product);
                    } else {
                        throw new MyException.InsufficientStockException("Brak dostępnych sztuk dla produktu id=" + id);
                    }
                },
                () -> System.out.println("Nie mozna dodac do koszyka, taki produkt nie istnieje")
        );

    }

    public Product remove(Long id) {
        Product removed = products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (removed == null) {
            return null;
        }

        products.remove(removed);
        return removed;
    }

    public void show() {
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

    public void finalizeCart() {
        products.forEach(product -> {
            product.setAvailableQuantity(product.getAvailableQuantity() - 1);
        });
        products.clear();
    }

    public boolean isEmpty(){
        return products.isEmpty();
    }

    public List<Product> getProducts() {
        return products;
    }
}
