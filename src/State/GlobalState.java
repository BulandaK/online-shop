package State;

import Manager.ProductManager;

public class GlobalState {
    private static ProductManager productManager;

    public static ProductManager getProductManager(){
        if(productManager==null){
            productManager = new ProductManager();
        }
        return productManager;
    }
}
