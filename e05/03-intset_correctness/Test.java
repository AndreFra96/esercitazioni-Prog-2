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
    }
}