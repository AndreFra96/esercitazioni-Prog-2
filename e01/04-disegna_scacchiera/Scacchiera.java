import java.util.Scanner;

public class Scacchiera {
    public static void main(String[] args) {
        int cellSize = 0;
        int rows = 8;
        int cols = 8;
        try (Scanner s = new Scanner(System.in)) {
            cellSize = s.nextInt();
        }

        disegnaScacchiera(cols, rows, cellSize);

    }

    /**
     * Post-condizioni: invia a stdout la rappresentazione di una scacchiera del
     * numero di righe/colonne inserite in input e con ogni cella di dimensione
     * cellSize
     */
    public static void disegnaScacchiera(int cols, int rows, int cellSize) {
        for (int i = 0; i < rows; i++) {
            for (int q = 0; q < cellSize; q++) {
                for (int j = 0; j < cols; j++) {
                    for (int k = 0; k < cellSize; k++) {
                        if (j % 2 == i % 2) {
                            drawChar(1, '-');
                        } else {
                            drawChar(1, '#');
                        }
                    }
                }
                drawChar(1, '\n');
            }
        }
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