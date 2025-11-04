package Models.Order;

import Models.Cart.Cart;

import java.math.BigDecimal;

public class Order {
    private Client client;
    private Cart cart;
    private BigDecimal orderPrice;

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

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }
}
