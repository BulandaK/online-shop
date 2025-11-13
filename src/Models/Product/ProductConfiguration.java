package Models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ProductConfiguration {
    private Long id;
    private String name;
    private BigDecimal price;
    private Product product;
    private List<Product> configuredProducts;

    public ProductConfiguration(Long id, String name, BigDecimal price, Product product, List<Product> configuredProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.product = product;
        this.configuredProducts = configuredProducts;
    }

    public ProductConfiguration(Long id, String name, BigDecimal price, Product product) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getConfiguredProducts() {
        return configuredProducts;
    }

    public void setConfiguredProducts(List<Product> configuredProducts) {
        this.configuredProducts = configuredProducts;
    }

    public void showConfiguration() {
        System.out.println("konfiugracja " + this);
        for (Product product : configuredProducts) {
            System.out.println("\t" + product);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductConfiguration that = (ProductConfiguration) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ProductConfiguration{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", product=" + product +
                '}';
    }
}
