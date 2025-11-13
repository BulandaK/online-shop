package Models.Product;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private int availableQuantity;
    private List<ProductConfiguration> configurations;

    public Product(Long id, String name, BigDecimal price, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public Product(Long id, String name, BigDecimal price, int availableQuantity, List<ProductConfiguration> configurations) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.configurations = configurations;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public List<ProductConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ProductConfiguration> configurations) {
        this.configurations = configurations;
    }

    public void showProduct() {
        System.out.println(this);
        if (configurations != null) {
            configurations.forEach(configuration -> System.out.println("\t" + configuration.getProduct()));
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", availableQuantity=" + availableQuantity +

                '}';
    }
}
