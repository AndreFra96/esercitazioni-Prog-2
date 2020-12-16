import java.util.Scanner;

public class Trasposizione {
    public static void main(String[] args) {

        int[][] matrix = new int[Integer.parseInt(args[0])][Integer.parseInt(args[1])];
        int index = 0;
        try (Scanner s = new Scanner(System.in)) {
            while (s.hasNextLine()) {
                String[] line = s.nextLine().split(" ");
                for (int i = 0; i < line.length; i++) {
                    matrix[index][i] = Integer.parseInt(line[i]);
                }
                index++;
            }
        }
        stampaMatrice(trasponi(matrix));
    }

    /**
     * Post-condizioni: invia a stdout la rappresentazione della matrice in input
     */
    public static void stampaMatrice(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Post-condizioni: Restituisce la matrice trasposta della matrice in input
     */
    public static int[][] trasponi(int[][] matrix) {
        int[][] returnMatrix = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                returnMatrix[j][i] = matrix[i][j];
            }
        }
        return returnMatrix;
    }
}