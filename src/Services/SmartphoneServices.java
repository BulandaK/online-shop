package Services;

import Models.Product.Smartphone;

import java.util.Scanner;

public class SmartphoneServices implements Configurable<Smartphone> {

    @Override
    public void configure(Smartphone smartphone) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Wybierz kolor: ");
        smartphone.setColor(sc.nextLine());

        System.out.print("Ustaw pojemnosc baterii: 100, 200, 300: ");
        smartphone.setBateryCapacity(sc.nextInt());

    }


}
