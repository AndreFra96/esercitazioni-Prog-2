import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Poly p = new Poly();
        try (Scanner s = new Scanner(new FileInputStream(new File("input-1.txt")))) {
            while (s.hasNextInt())
                p = p.add(new Poly(s.nextInt(), s.nextInt()));
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        Iterator<Integer> it = p.degrees();
        while (it.hasNext())
            System.out.println(it.next());
    }
}
