package Manager;

import Models.Order.Order;

import java.util.Scanner;

public class OrderManager {

    private final ProductManager productManager;
    private final Scanner scanner = new Scanner(System.in);

    public OrderManager() {
        this.productManager = State.GlobalState.getProductManager();
    }

    public void showProducts() {
        productManager.showInventory();
    }

    public void addToCart(Order order) {
        try{
            Long id = getIdFromUser("\n\nwybierz id produktu ktory chcesz dodac do koszyka");

            order.getCart().add(id);
            order.getCart().show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void removeFromCart(Order order) {
        try {
            Long id = getIdFromUser("\n\nwybierz id produktu ktory chcesz usunac z koszyka");
            order.removeFromCart(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void executeOrder(Order order) {
        try {
            order.makeOrder();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private boolean isEmptyCart(Order order){
        return order.getCart().isEmpty();
    }
    private Long getIdFromUser(String message) {
        System.out.println(message);
        Long id = scanner.nextLong();
        scanner.nextLine();
        return id;
    }
}
