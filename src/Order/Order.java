package Order;

import Cart.Cart;

public class Order {
    private Client client;
    private Cart cart;
    private double orderSum;

    public Order(Client client, Cart cart) {
        this.client = client;
        this.cart = cart;
        this.orderSum = cart.sumPrices();
    }


}
