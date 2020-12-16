import java.util.Scanner;

public class Pegi {
    public static void main(String[] args) {
        int age = 0;
        try (Scanner s = new Scanner(System.in)) {
            age = s.nextInt();
        }
        printPegi(age);
    }

    public static void printPegi(int age) {
        String result = "fascia 3";

        if (age >= 7 && age <= 11) {
            result = "fascia 7";
        } else if (age >= 12 && age <= 15) {
            result = "fascia 12";
        } else if (age >= 16 && age <= 17) {
            result = "fascia 16";
        } else if (age >= 18) {
            result = "fascia 18";
        }

        System.out.println(result);
    }

}