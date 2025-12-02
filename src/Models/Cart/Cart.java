package Models.Cart;

import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Product;
import Services.Discount.DiscountStrategy; // Jeśli zaimplementowałeś Task 12

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> products;
    private DiscountStrategy discountStrategy; // Z Task 12

    public Cart() {
        this.products = new ArrayList<>();
    }

    // Metody "techniczne" tylko do obsługi listy
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void clear() {
        products.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    // Tę metodę zostawiamy w modelu, bo oblicza stan wewnętrzny obiektu
    public BigDecimal sumPrices() {
        BigDecimal baseTotal = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (discountStrategy != null) {
            return discountStrategy.applyDiscount(baseTotal);
        }
        return baseTotal;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    // Factory method może zostać lub można ją przenieść (zostawmy dla wygody)
    public Order makeOrder(Client client) {
        return new Order(client, this);
    }
}