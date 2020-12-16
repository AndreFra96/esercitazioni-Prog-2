import java.util.Scanner;

public class ProvaDelNoveCopy {
    public static void main(String[] args) {
        int max = 0;
        try (Scanner s = new Scanner(System.in)) {
            max = s.nextInt();
            for (int i = 0; i < max; i++) {
                for (int j = 0; j < max; j++) {
                    for (int k = 0; k < max; k++) {
                        if ((i * j != k) && provaDelNove(i, j, k)) {
                            System.out.println(i + " " + j + " " + k);
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>
     * Post-condizioni: restituisce true se la prova del nove sui numeri in input
     * risulta corretta, false altrimenti
     */
    public static boolean provaDelNove(int moltiplicando, int moltiplicatore, int risultato) {
        moltiplicando = sommaCifreRicorsiva(moltiplicando);
        moltiplicatore = sommaCifreRicorsiva(moltiplicatore);
        risultato = sommaCifreRicorsiva(risultato);
        return sommaCifreRicorsiva(moltiplicando * moltiplicatore) == risultato;
    }

    /**
     * <p>
     * Pre-condizioni: sono ammessi in input solo numeri interi positivi
     * <p>
     * Post-condizioni: restituisce un intero di una cifra corrispondente alla somma
     * ricorsiva delle cifre del numero in input
     */
    public static int sommaCifreRicorsiva(int number) {
        int result = 0;

        while(number > 0) {
            result += number % 10;
            number = number / 10;
        }

        if (result > 9) {
            result = sommaCifreRicorsiva(result);
        }

        return result;
    }

}