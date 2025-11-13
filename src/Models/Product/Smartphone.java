package Models.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class Smartphone extends Product {
    private String color;
    private int bateryCapacity;

    public Smartphone(Long id, String name, BigDecimal price, int availableQuantity, String color, int bateryCapacity) {
        super(id, name, price, availableQuantity);
        this.color = color;
        this.bateryCapacity = bateryCapacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBateryCapacity() {
        return bateryCapacity;
    }

    public void setBateryCapacity(int bateryCapacity) {
        this.bateryCapacity = bateryCapacity;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Smartphone smartphone = (Smartphone) o;
        return getId() == smartphone.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "Smartphone{" +
                "id=" + getId() +
                ", color='" + color +
                ", bateryCapacity=" + bateryCapacity +
                '}';
    }
}
