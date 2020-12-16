import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        List<CorpoCeleste> sa = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        // Scanner s = new Scanner(new FileInputStream(new File("input-1.txt")));
        while (s.hasNext()) {
            char pOrS = s.next().charAt(0); // can be P or S
            String name = s.next();
            int x = s.nextInt();
            int y = s.nextInt();
            int z = s.nextInt();
            switch (pOrS) {
                case 'P':
                    sa.add(new Pianeta(name, new Punto(x, y, z), new Punto(0, 0, 0)));
                    break;
                case 'S':
                    sa.add(new StellaFissa(name, new Punto(x, y, z)));
                    break;
                default:
                    break;
            }
        }
        SistemaAstronomico sistemaAstronomico = new SistemaAstronomico("Sistema Solare", sa);
        for (int i = 0; i < Integer.parseInt(args[0]); i++)
            sistemaAstronomico.updateStatus();
        for (CorpoCeleste actual : sistemaAstronomico)
            System.out.println(actual);

        System.out.println("Energia totale: " + sistemaAstronomico.totalEnergy());

    }
}