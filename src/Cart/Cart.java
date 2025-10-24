package Cart;

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

    public void showCart(){
        System.out.println("koszyk uzytkownika:");
        for (Product product : userCart) {
            System.out.println(product);
        }
    }

    public void makeOrder(){
        System.out.println("skladam zamowienie");
    }

    public double sumPrices(){
        return userCart.stream()
                .mapToDouble(product -> product.getPrice())
                .sum();
    }
    public List<Product> getUserCart() {
        return userCart;
    }
}
