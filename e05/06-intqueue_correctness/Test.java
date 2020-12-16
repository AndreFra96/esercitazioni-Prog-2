import java.util.Scanner;

/** Test */
public class Test {

  public static void main(String[] args) {
    IntQueue queue;
    int nEnc = 0, nDec = 0;

    try (Scanner s = new Scanner(System.in)) {
      int n = s.nextInt();
      queue = new IntQueue(n);
      while (s.hasNextByte()) {
        byte op = s.nextByte();
        if (op == +1) {
          if (queue.size() == n) break;
          queue.enqueue(++nEnc);
        } else {
          if (nDec == nEnc) break;
          System.out.println(queue.dequeue());
          nDec++;
        }
      }
    }
    System.out.println(queue);
    System.out.println(queue.size());
  }
}