import java.util.Scanner;

public class V {
    public static void main(String[] args) {
        int lines = 0;
        try (Scanner s = new Scanner(System.in)) {
            lines = s.nextInt();
        }
        drawV(lines);
    }

    /**
     * Post-condizioni: disegna una v in stdout di lines righe
     */
    public static void drawV(int lines) {
        int leftSpaces = 0;
        int centerSpaces = lines > 1 ? (2 * lines) - 3 : 0;

        for (int i = 0; i < lines; i++) {
            drawChar(leftSpaces, ' ');
            drawChar(1, '*');
            drawChar(centerSpaces, ' ');

            if (centerSpaces != -1 && centerSpaces != 0)
                drawChar(1, '*');

            drawChar(1, '\n');

            leftSpaces += 1;
            centerSpaces -= 2;

        }
    }

    /**
     * Post-condizioni: invia a stdout una serie di caratteri corrispondente al carattere in input ripetuto n volte
     */
    public static void drawChar(int n, char c) {
        for (int i = 0; i < n; i++) {
            System.out.print(c);
        }
    }
}