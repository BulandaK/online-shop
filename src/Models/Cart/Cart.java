package Models.Cart;

import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Product;
import Services.Discount.DiscountStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> products;
    private DiscountStrategy discountStrategy;

    public Cart() {
        this.products = new ArrayList<>();
    }

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

    public Order makeOrder(Client client) {
        return new Order(client, this);
    }
}