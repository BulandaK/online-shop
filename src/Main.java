import Manager.ProductManager;
import Product.Computer;

public class Main {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();
        Computer myComp = new Computer(1,"lenovo",2.4,5,"i5",32);
        manager.addToInventory(myComp);
        manager.showInventory();

        Computer updatedComp = new Computer(1,"msi",5,6,"i5",16);

        manager.updateProduct(1,updatedComp);
        manager.showInventory();

    }
}