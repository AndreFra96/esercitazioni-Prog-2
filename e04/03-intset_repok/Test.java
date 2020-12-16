import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        IntSet set = new IntSet();
        try(Scanner s = new Scanner(System.in)){
            while(s.hasNextInt()){
                set.insert(s.nextInt());
            }
        }
        System.out.println(set.size());

        // IntSet newset = new IntSet();
        // newset.insert(1);
        // newset.insert(2);
        // newset.insert(3);

        // IntSet newset1 = new IntSet();
        // newset1.insert(3);
        // newset1.insert(1);
        // newset1.insert(2);

        // System.out.println(newset.hashCode());
        // System.out.println(newset1.hashCode());
        // System.out.println(newset.equals(newset1));

        // System.out.println(newset.hashCode());
        // System.out.println(newset1.hashCode());
        // System.out.println(newset.equals(newset1));
    }
}