import java.util.Scanner;

public class Kaprekar {
    public static void main(String[] args) {
        int number = 0;
        try (Scanner s = new Scanner(System.in)) {
            number = s.nextInt();
        }
        passiDiKaprekar(number);
    }

    /**
     * Pre-condizioni: il numero in input deve essere un numero di 4 cifre
     * Post-condizioni: calcola e invia a stdout la sequenza di numeri necessaria
     * per giungere alla costante di kaprekar a partire dal numero in input
     */
    public static void passiDiKaprekar(int number) {
        System.out.println(number);
        if (number == 6174)
            return;
        int[] array = intToArray(number);
        int differenza = intArrayToInt(sort(array, true)) - intArrayToInt(sort(array, false));
        passiDiKaprekar(differenza);
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

    /**
     * Post-condizioni: restituisce un array di n posizioni ( n = numero di cifre
     * del numero) con una cifra del numero in ogni posizione, mantenendo l'ordine
     * delle cifre
     */
    public static int[] intToArray(int number) {
        int[] cifre = new int[contaCifre(number)];
        int index = 0;
        while (number > 0) {
            cifre[index] = number % 10;
            number = number / 10;
            index++;
        }
        return cifre;
    }

    /**
     * Post-condizioni: restituisce il numero di cifre di cui è composto il numero
     * in input
     */
    public static int contaCifre(int number) {
        if (number < 0)
            number *= -1;
        int count = 0;
        while (number > 0) {
            count++;
            number /= 10;
        }
        return count;
    }

    /**
     * Post-condizioni: restituisce un numero intero corrispondente alla
     * concatenazione dei vari elementi dell'array mantenendo il loro ordine, se la
     * lunghezza dell'array è 0 restituisce 0
     */
    public static int intArrayToInt(int[] array) {
        int result = 0;
        int shift = 1;
        for (int i = array.length - 1; i >= 0; i--) {
            result += array[i] * shift;
            shift *= 10;
        }
        return result;
    }

}