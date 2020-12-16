import java.util.Scanner;

public class Tartaglia {
    public static void main(String[] args){
        int line = 0;
        try(Scanner s = new Scanner(System.in)){
            line = s.nextInt();
        }
        int[] tartagliaLine = getTartagliaLine(line);
        for(int i = 0; i < tartagliaLine.length; i++){
            System.out.printf("%d ",tartagliaLine[i]);
        }
    }

    public static int[] getTartagliaLine(int line){
        int[] result = {1};
        for(int i = 1; i < line; i++){
            result = nexLineTartaglia(result);
        }
        return result;
    }

    /**
     * Pre-condizioni: per un risultato corretto l'array in input deve rappresentare una riga valida del triangolo di tartaglia
     * Post-condizioni: restituisce la riga successiva a quella inserita in input del triangolo di tartaglia
     */
    public static int[] nexLineTartaglia(int[] previous){
        int[] next = new int[previous.length+1];
        next[0] = 1;
        next[previous.length] = 1;
        for(int i = 1; i < previous.length; i++){
            next[i] = previous[i-1] + previous[i];
        }
        return next;
    }
}