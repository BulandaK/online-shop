package Manager;

import Product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Product> userCart;

    public Cart(){
        userCart = new ArrayList<Product>();
    }

    public void addToCart(Product product){
        userCart.add(product);
    }

    public void removeFromCart(int id){
        userCart.removeIf(p->p.getId()==id);
    }

}
