import java.util.Scanner;

public class Saltapicchio {
    public static void main(String[] args) {
        int[] numbers = new int[Integer.parseInt(args[0])];
        try (Scanner s = new Scanner(System.in)) {
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = s.nextInt();
            }
        }
        if (isSaltapicchio(numbers))
            System.out.println("saltapicchio");
    }

    /**
     * Post-condizioni: restituisce true se la sequenza inserita è un saltapicchio,
     * false altrimenti
     */
    public static boolean isSaltapicchio(int[] numbers) {
        if(numbers.length <= 1) return true;
        int[] calculated = new int[numbers.length - 1];
        for (int i = 1; i < numbers.length; i++) {
            int result = numbers[i - 1] - numbers[i];
            if (result < 0)
                result *= -1;
            calculated[i - 1] = result;
        }
        return isFullSequence(calculated);
    }

    /**
     * Post-condizioni: restituisce true se la sequenza di numeri interi in input è
     * completa, ovvero sono presenti tutti gli elementi da values.length - 1 a 1,
     * false altrimenti
     */
    public static boolean isFullSequence(int[] values) {
        values = sort(values, true);
        for (int i = 0; i < values.length; i++) {
            if (values[i] != values.length - i)
                return false;
        }
        return true;
    }

    /**
     * Post-condizioni: restituisce un array di interi corrispondente all'array di
     * interi in input ordinato in modo crescente se reverse è false, altrimenti in
     * ordine decrescente
     */
    public static int[] sort(int[] number, boolean reverse) {
        for (int i = 0; i < number.length; i++) {
            for (int j = 0; j < number.length; j++) {
                if ((reverse && number[i] > number[j]) || (!reverse && number[i] < number[j])) {
                    int temp = number[i];
                    number[i] = number[j];
                    number[j] = temp;
                }
            }
        }
        return number;
    }

}