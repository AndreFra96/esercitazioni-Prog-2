import java.util.Scanner;

public class Rombo {
    public static void main(String[] args) {
        int input = 1;
        try (Scanner s = new Scanner(System.in)) {
            input = s.nextInt();
        }
        disegnaRombo(input,'*');
    }

    /**
     * Post-condizioni: invia a stdout la rappresentazione di un rombo con diagonale minore = (input * 2) + 1, rappresentata con il carattere c
     */
    public static void disegnaRombo(int input, char c) {
        int fill = 1;
        int ipos = 0;
        for (int i = -input; i <= input; i++) {
            if (i < 0) { // TODO: trovare soluzione migliore
                ipos = i * -1;
            } else {
                ipos = i;
            }
            printLine(ipos, fill, '*');

            if (i < 0) { // TODO: trovare soluzione migliore
                fill += 2;
            } else {
                fill -= 2;
            }
        }
    }

    /**
     * Post-condizioni: invia a stdout una riga composta da spaces spazi, fill
     * caratteri c e \n alla fine
     */
    public static void printLine(int spaces, int fill, char c) {
        drawChar(spaces, ' ');
        drawChar(fill, c);
        drawChar(1, '\n');
    }

    /**
     * Post-condizioni: invia a stdout una serie di caratteri corrispondente al
     * carattere in input ripetuto n volte
     */
    public static void drawChar(int n, char c) {
        for (int i = 0; i < n; i++) {
            System.out.print(c);
        }
    }

}