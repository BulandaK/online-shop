package Models.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class Computer extends Product {
    private String processor;
    private int ram;

    public Computer(Long id, String name, BigDecimal price, int availableQuantity, String processor, int ram) {
        super(id, name, price, availableQuantity);
        this.processor = processor;
        this.ram = ram;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + getId() +
                ", procesor='" + processor +
                ", ram=" + ram +
                ", name=" + getName() +
                ", price=" + getPrice() +
                ", availableQuantity=" + getAvailableQuantity() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Computer computer = (Computer) o;
        return getId() == computer.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}
