package Product;

public class Electronics extends Product{
    public Electronics(int id, String name, double price, int availableQuantity) {
        super(id, name, price, availableQuantity);
    }
    @Override
    public void configure(){
        System.out.println("Brak dodatkowej konfiguracji dla typu Electronics");
    }
}
