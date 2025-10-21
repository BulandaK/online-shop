package Product;

import java.util.Objects;
import java.util.Scanner;

public class Smartphone extends Product {
    private String color;
    private int bateryCapacity;

    public Smartphone(int id, String name, double price, int availableQuantity) {
        super(id, name, price, availableQuantity);
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
    public void configure() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Wybierz kolor (czarny / biały / niebieski): ");
        color = scanner.nextLine();

        System.out.print("Wybierz pojemność baterii (3000 / 4000 / 5000 mAh): ");
        bateryCapacity = scanner.nextInt();


        System.out.println("Skonfigurowano smartfon: kolor " + color + ", " + bateryCapacity);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Smartphone that = (Smartphone) o;
        return bateryCapacity == that.bateryCapacity && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, bateryCapacity);
    }

    @Override
    public String toString() {
        return "Smartphone{" +
                "color='" + color + '\'' +
                ", bateryCapacity=" + bateryCapacity +
                '}';
    }
}
