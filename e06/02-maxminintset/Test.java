import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        MaxMinIntSet set = new MaxMinIntSet();
        try(Scanner s = new Scanner(System.in)){
            while(s.hasNextInt()){
                set.insert(s.nextInt());
            }
        }
        System.out.println(set.size());
        System.out.println(set.min());
        System.out.println(set.max());

    }
}