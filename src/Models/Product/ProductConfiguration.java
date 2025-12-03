package Models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Represents a specific configuration of a product.
 * <p>
 * This is used for creating bundles or specific setups (e.g., a computer with specific components).
 * It holds a reference to the main product and a list of sub-products.
 * </p>
 */
public class ProductConfiguration {
    private Long id;
    private String name;
    private BigDecimal price;
    private Product product;

    /**
     * A list of sub-products that make up this configuration.
     */
    private List<Product> configuredProducts;

    /**
     * Constructs a full configuration with sub-products.
     *
     * @param id                 The unique identifier.
     * @param name               The name of the configuration.
     * @param price              The price of the configuration.
     * @param product            The main product this configuration belongs to.
     * @param configuredProducts The list of component products.
     */
    public ProductConfiguration(Long id, String name, BigDecimal price, Product product, List<Product> configuredProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.product = product;
        this.configuredProducts = configuredProducts;
    }

    /**
     * Constructs a basic configuration without sub-products initially.
     *
     * @param id      The unique identifier.
     * @param name    The name of the configuration.
     * @param price   The price of the configuration.
     * @param product The main product this configuration belongs to.
     */
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

    /**
     * Displays the configuration details and its components to the console.
     */
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