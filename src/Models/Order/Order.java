package Models.Order;

import Models.Cart.Cart;
import Models.Product.Product;
import MyException.EmptyCartException;
import MyException.ProductNotFoundException;
import Services.InvoicePersistence;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a purchase order placed by a client.
 * <p>
 * Links a {@link Client} with a {@link Cart} and calculates the initial order price.
 * </p>
 */
public class Order {
    private Client client;
    private Cart cart;

    /**
     * The total price of the order at the moment of creation.
     */
    private BigDecimal orderPrice;

    /**
     * Creates a new Order.
     *
     * @param client The client placing the order.
     * @param cart   The shopping cart containing the items to be purchased.
     */
    public Order(Client client, Cart cart) {
        this.client = client;
        this.cart = cart;
        this.orderPrice = cart.sumPrices();
    }

    public Client getClient() {
        return client;
    }

    public Cart getCart() {
        return cart;
    }

    /**
     * Gets the total price of the order.
     *
     * @return The order price as BigDecimal.
     */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }
}