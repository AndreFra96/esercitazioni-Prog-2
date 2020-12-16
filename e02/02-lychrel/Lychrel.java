import java.util.Scanner;

public class Lychrel {

    /*
     * Post-condizioni: restituisce una rappresentazione, sotto forma di stringa,
     * del numero fornito in input
     */
    public static String fromLongToString(long n) {
        return "" + n;
    }

    /*
     * Post-condizioni: restituisce true se s è palindroma, false altrimenti
     *                  throw NullPointerException se la stringa in input è null
     */
    public static boolean isPalindrome(String s) {
        if (s == null)
            throw new NullPointerException("La stringa in input non può essere null");
        int len = s.length();
        if (len <= 1)
            return true;
        return s.charAt(0) == s.charAt(len - 1) && isPalindrome(s.substring(1, len - 1));
    }

    /*
     * Post-condizioni: restituisce il reversal della stringa s (s "capovolta")
     * throws NullPointerException se la stringa in input è null
     */
    public static String reverse(String s) {
        if (s == null)
            throw new NullPointerException("La stringa in input non può essere null");
        int len = s.length();
        if (len <= 1)
            return s;
        return s.charAt(len - 1) + reverse(s.substring(1, len - 1)) + s.charAt(0);
    }

    /*
     * Post-condizioni: restituisce il valore successivo nella Sequenza di Lychrel
     * throws NumberFormatException se non è possibile eseguire il cast della
     * stringa in un long
     */
    public static long lychrelStep(long n) {
        try {
            return n + Long.parseLong((reverse(fromLongToString(n))));
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    /*
     * Pre-condizioni: n non è un numero di Lychrel Post-condizioni: stampa la
     * Sequenza di Lychrel a partire da n
     */
    public static void lychrelSequence(long n) {
        while (!isPalindrome(fromLongToString(n))) {
            System.out.println(n);
            n = lychrelStep(n);
        }
        System.out.println(n);
    }

    public static void main(String[] args) {
        try (Scanner s = new Scanner(System.in)) {
            lychrelSequence(s.nextLong());
        }
    }
}
