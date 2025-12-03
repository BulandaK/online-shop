package Models.Cart;

import Models.Order.Client;
import Models.Order.Order;
import Models.Product.Product;
import Services.Discount.DiscountStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping cart that holds selected products.
 * <p>
 * This class manages the list of products selected by the user and handles
 * total price calculation, including the application of discount strategies.
 * </p>
 */
public class Cart {
    /**
     * The list of products currently in the cart.
     */
    private final List<Product> products;

    /**
     * The strategy used to calculate the final price (e.g., applying discounts).
     * Can be null if no discount is applied.
     */
    private DiscountStrategy discountStrategy;

    /**
     * Creates a new, empty shopping cart.
     */
    public Cart() {
        this.products = new ArrayList<>();
    }

    /**
     * Adds a product to the cart.
     *
     * @param product The product to be added.
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Removes a specific product instance from the cart.
     *
     * @param product The product to be removed.
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }

    /**
     * Retrieves the list of products currently in the cart.
     *
     * @return A list of {@link Product} objects.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Removes all products from the cart.
     */
    public void clear() {
        products.clear();
    }

    /**
     * Checks if the cart contains any products.
     *
     * @return {@code true} if the cart is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return products.isEmpty();
    }

    /**
     * Calculates the total price of all products in the cart.
     * <p>
     * If a {@link DiscountStrategy} is set, it is applied to the base total
     * to calculate the final discounted price.
     * </p>
     *
     * @return The total price (possibly discounted) as a {@link BigDecimal}.
     */
    public BigDecimal sumPrices() {
        BigDecimal baseTotal = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (discountStrategy != null) {
            return discountStrategy.applyDiscount(baseTotal);
        }
        return baseTotal;
    }

    /**
     * Sets the discount strategy to be used for price calculation.
     *
     * @param discountStrategy The strategy implementation (e.g., PercentageDiscount).
     */
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    /**
     * Creates a new Order based on the current contents of this cart.
     *
     * @param client The client placing the order.
     * @return A new {@link Order} object linked to this cart.
     */
    public Order makeOrder(Client client) {
        return new Order(client, this);
    }
}