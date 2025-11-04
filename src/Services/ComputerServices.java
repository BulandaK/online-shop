package Services;

import Models.Product.Computer;

import java.util.Scanner;

public class ComputerServices implements Configurable<Computer> {

    @Override
    public void configure(Computer computer) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Wybierz procesor (i5,i7,i9): ");
        computer.setProcessor(sc.nextLine());

        System.out.print("Wybierz RAM (8/16/32 GB): ");
        computer.setRam(sc.nextInt());
    }


}
