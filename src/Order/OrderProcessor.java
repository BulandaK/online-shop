package Order;

import Manager.ProductManager;

public class OrderProcessor {


    public Invoice processOrder(Order order){

        Invoice invoice = new Invoice(
                "FV/"+ Math.random(),
                order.getClient(),
                order.getCart(),
                0.23
        );
        return invoice;
    }


}
