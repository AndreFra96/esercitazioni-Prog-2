
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        OrderedIntList li = new OrderedIntList();
        // try (Scanner s = new Scanner(new FileInputStream(new File("positive-negative-input.txt")))) {
        //     while (s.hasNextInt()) {
        //         li.add(s.nextInt());
        //     }
        // }
        // catch(FileNotFoundException e){
        //     System.out.println(e);
        // }

        try (Scanner s = new Scanner(System.in)) {
            while (s.hasNextInt()) {
                li.add(s.nextInt());
            }
        }


        StringBuilder sb = new StringBuilder();

        Iterator<Integer> stb = li.smallToBig();
        while (stb.hasNext()) {
            Integer next = stb.next();
                sb.append(next);
                if (stb.hasNext()) {
                    sb.append(", ");
                }
        }

        sb.append("\n");

        Iterator<Integer> bts = li.bigToSmall();
        while (bts.hasNext()) {
            Integer next = bts.next();
                sb.append(next);
                if (bts.hasNext()) {
                    sb.append(", ");
                }
        }

        System.out.println(sb.toString());

    }
}