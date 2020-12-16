import java.util.Scanner;

public class TestRunner {

  private static void print(Polinomio p) {
    final int degree = p.degree();
    for (int i = 0; i <= degree; i++)
      System.out.printf("%d %d\n", p.coeff(i), i);
    System.out.println();
  }

  public static void main(String[] args) {
    try (Scanner s = new Scanner(System.in)) {
      int termsP = s.nextInt(), termsQ = s.nextInt();
      Polinomio p = new Polinomio(), q = new Polinomio();
      for (int i = 0; i < termsP; i++)
        p = p.add(new Polinomio(s.nextInt(), s.nextInt()));
      print(p);
      for (int i = 0; i < termsQ; i++)
        q = q.add(new Polinomio(s.nextInt(), s.nextInt()));
      print(q);
      print(p.minus());
      print(p.add(q));
      print(p.sub(q));
      print(p.mul(q));
    }
  }
}
