import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        List<Razionale> list = new ArrayList<>();
        int countEquals = 0;
        try (Scanner s = new Scanner(System.in)) {
            while (s.hasNextInt()) {
                Razionale read = new Razionale(s.nextInt(), s.nextInt());
                if (list.contains(read))
                    countEquals++;

                list.add(read);
            }
        }

        if (list.size() > 0) {
            Razionale somma = list.get(0);
            Razionale moltiplicazione = list.get(0);
            Razionale divisione = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                Razionale temp = list.get(i);
                somma = somma.add(temp);
                moltiplicazione = moltiplicazione.mul(temp);
                divisione = divisione.div(temp);
            }
            System.out.println(somma);
            System.out.println(moltiplicazione);
            System.out.println(divisione);
        }

        System.out.println(countEquals);

    }
}