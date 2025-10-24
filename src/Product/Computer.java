package Product;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Computer extends Product {
    private String processor;
    private int ram;

    private static final String[] BRANDS = {"Lenovo", "MSI", "Asus", "Acer", "Dell"};
    private static final String[] CPUs = {"i5", "i7", "i9"};
    private static final Random random = new Random();

    public Computer(int id, String name, double price, int availableQuantity,String processor,int ram) {
        super(id, name, price, availableQuantity);
        this.processor = processor;
        this.ram = ram;
    }

    @Override
    public void configure() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Wybierz procesor (i5,i7,i9):");
        processor = sc.nextLine();

        System.out.println("wybierz ilosc RAM (8/16/32 GB)");
        ram = sc.nextInt();
        sc.nextLine();

        System.out.println("Skonfigurowano komputer: " + processor + ", RAM: " + ram + " GB");
    }

    @Override
    public String toString() {
        return "Computer{" +
                "procesor='" + processor + '\'' +
                ", ram=" + ram +
                ", id=" + getId() +
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
        return Objects.hash(processor, ram);
    }

    public static Computer createRandomComputer(int id) {
        String brand = BRANDS[random.nextInt(BRANDS.length)];
        double price = 500 + random.nextInt(1500);
        int quantity = 1 + random.nextInt(10);
        String cpu = CPUs[random.nextInt(CPUs.length)];
        int ram = 8 * (1 + random.nextInt(4));

        return new Computer(id, brand, price, quantity, cpu, ram);
    }
}
