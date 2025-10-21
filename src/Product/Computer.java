package Product;

import java.util.Objects;
import java.util.Scanner;

public class Computer extends Product {
    private String processor;
    private int ram;

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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Computer computer = (Computer) o;
        return ram == computer.ram && Objects.equals(processor, computer.processor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processor, ram);
    }


}
