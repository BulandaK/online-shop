import Cart.Cart;
import Manager.ProductManager;
import Product.Computer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();
        List<Computer> computersList = new ArrayList<>();

        Cart userCart = new Cart();


        for (int i = 0; i < 10; i++) {
            Computer computer =Computer.createRandomComputer(i);
            manager.addToInventory(computer);
            userCart.addToCart(computer);
//            computersList.add(Computer.createRandomComputer(i));
        }

        userCart.showCart();
        System.out.println(userCart.sumPrices());

    }

}