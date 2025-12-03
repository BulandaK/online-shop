package Models.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a product available in the online shop.
 * <p>
 * A product has a name, price, stock quantity, and optional configurations
 * (e.g., for bundled products or computer sets).
 * </p>
 */
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private int availableQuantity;

    /**
     * List of possible configurations associated with this product.
     * Useful for complex items like computers.
     */
    private List<ProductConfiguration> configurations;

    /**
     * Constructs a standard product.
     *
     * @param id                The unique identifier.
     * @param name              The name of the product.
     * @param price             The price of the product.
     * @param availableQuantity The initial stock quantity.
     */
    public Product(Long id, String name, BigDecimal price, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    /**
     * Constructs a product with specific configurations.
     *
     * @param id                The unique identifier.
     * @param name              The name of the product.
     * @param price             The price of the product.
     * @param availableQuantity The initial stock quantity.
     * @param configurations    A list of {@link ProductConfiguration} objects.
     */
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

    /**
     * Checks if the product is currently in stock.
     *
     * @return {@code true} if available quantity is greater than 0, {@code false} otherwise.
     */
    public boolean isAvailable() {
        return this.availableQuantity > 0;
    }

    public List<ProductConfiguration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ProductConfiguration> configurations) {
        this.configurations = configurations;
    }

    /**
     * Displays the product details and its configurations (if any) to the console.
     */
    public void showProduct() {
        System.out.println(this);
        if (configurations != null) {
            configurations.forEach(configuration -> System.out.println("\t" + configuration.getProduct()));
        }
    }

    /**
     * Creates a configuration instance based on this product.
     *
     * @param id The ID for the new configuration.
     * @return A new {@link ProductConfiguration} linked to this product.
     */
    public ProductConfiguration makeProductAsConfiguration(Long id) {
        return new ProductConfiguration(id, this.name, this.price, this);
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