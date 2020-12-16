import java.util.Scanner;

public class Collatz {
    public static void main(String[] args) {
        int start = 0;
        try (Scanner s = new Scanner(System.in)) {
            start = s.nextInt();
        }
        step(start, 1);
    }

    private static void step(int number, int count) {
        System.out.printf("%d ", number);
        if (number != 1) {
            count++;
            step(number % 2 == 0 ? number / 2 : (number * 3 + 1), count);
        } else {
            System.out.printf("%d ", count);
        }
    }

}