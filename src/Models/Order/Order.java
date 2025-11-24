package Models.Order;

import Models.Cart.Cart;
import Models.Product.Product;
import MyException.EmptyCartException;
import MyException.ProductNotFoundException;

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

    public void makeOrder() throws EmptyCartException {
        if (cart.isEmpty()) {
            throw new EmptyCartException("Nie można wykonać zamówienia, koszyk jest pusty!");
        }

        Invoice invoice = new Invoice(
                "FV/" + Math.random(),
                client,
                cart,
                0.23
        );

        System.out.println("Faktura wygenerowana! ");
        invoice.showInvoice();

        cart.finalizeCart();
    }

    public void removeFromCart(Long id) throws EmptyCartException {

        if(cart.isEmpty()){
            throw new EmptyCartException("Nie można usunać z koszyka jest pusty!");
        }

        Product removedProduct = cart.remove(id);
        if (removedProduct != null) {
            System.out.println("usuneles z koszyka product: " + removedProduct);
            cart.show();
        }else {
            throw new ProductNotFoundException("Nie ma w koszyku produktu, który chcesz usunąć");
        }
    }
}
