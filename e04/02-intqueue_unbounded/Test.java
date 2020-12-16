import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        UnboundedQueue coda = new UnboundedQueue();
        try (Scanner s = new Scanner(System.in)) {
            int count = 0;

            while (s.hasNextInt()) {
                int next = s.nextInt();
                if (next == 1) {
                    count++;
                    coda.enqueue(count);
                } else if (next == -1) {
                    if (coda.size() > 0) {
                        System.out.println(coda.dequeue());
                    } else {
                        break;
                    }
                }
            }
            System.out.println(coda);
            System.out.println(coda.size());

        }
    }
}