import java.util.Iterator;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        UnboundedQueue q = new UnboundedQueue();
        int count = 0;
        try (Scanner s = new Scanner(System.in)) {
            while (s.hasNextInt()) {
                count++;
                if(count % 3 == 0) q.dequeue();
                q.enqueue(s.nextInt());
            }
        }

        Iterator<Integer> it = q.elements();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}