import java.util.Arrays;
import java.util.Objects;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano dei polinomi, Poly è
 * senza limiti ed immutabile
 */
public class Poly {
    private final int[] poly;

    /**
     * Post-condizioni: Inizializza un nuovo polinomio 0, il polinomio 0 è il
     * polinomio 0^0
     */
    public Poly() {
        poly = new int[1];
    }

    /**
     * Post-condizioni: Inizializza un nuovo polinomio di grado n (cx^n), se c è
     * uguale a 0 inizializzo un polinomio zero
     * <p>
     * solleva NegativeExponentException se n è minore di 0
     */

    public Poly(int c, int n) {
        if (n < 0)
            throw new NegativeExponentException();
        poly = new int[n + 1];
        poly[n] = c;
    }

    /**
     * Post-condizioni: inizializza un nuovo polinomio attraverso l'array in input
     * solleva NullPointerException se p è null
     */
    private Poly(int[] p) {
        if (p.length == 0)
            throw new IllegalArgumentException("p non rappresenta un polinomio valido");
        this.poly = Objects.requireNonNull(p);
    }

    /**
     * Post-condizioni: Restituisce un array di interi corrispondente agli elementi
     * di poly ordinati
     */
    private int[] getElements() {
        return Arrays.copyOf(poly, poly.length);
    }

    /**
     * Post-condizioni: restituisce un intero corrispondente al grado del polinomio
     * attuale
     */
    public int degree() {
        return poly.length - 1;
    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente alla somma di
     * this e q
     * <p>
     * solleva NullPointerException se q è null
     */
    public Poly add(Poly q) {
        Objects.requireNonNull(q);
        int[] smaller = q.degree() > degree() ? this.getElements() : q.getElements();
        int[] bigger = q.degree() > degree() ? q.getElements() : this.getElements();
        int[] result = new int[bigger.length];
        for (int i = 0; i < smaller.length; i++)
            bigger[i] += smaller[i];

        // Accorcio l'array fino ad avere nell'ultima posizione un coefficente diverso
        // da 0 o fino a raggiungere lunghezza 1
        int emptySpot = 0;
        for (int i = result.length - 1; i > 0; i--) {
            if (bigger[i] == 0) {
                emptySpot++;
            } else {
                break;
            }
        }

        bigger = Arrays.copyOf(bigger, bigger.length - emptySpot);

        return new Poly(bigger);
    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente alla
     * differenza fra this e q
     * <p>
     * solleva NullPointerException se q è null
     */
    public Poly sub(Poly q) {
        Objects.requireNonNull(q);
        return this.add(q.minus());
    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente al prodotto
     * fra this e q
     * <p>
     * solleva NullPointerException se q è null
     */
    public Poly mul(Poly q) {
        Objects.requireNonNull(q);
        int[] smaller = q.degree() > degree() ? this.getElements() : q.getElements();
        int[] bigger = q.degree() > degree() ? q.getElements() : this.getElements();
        Poly prodotto = new Poly();

        for (int i = 0; i < smaller.length; i++) {
            for (int j = 0; j < bigger.length; j++) {
                prodotto = prodotto.add(new Poly(smaller[i] * bigger[j], i + j));
            }
        }
        return prodotto;
    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio, corrispondente al polinomio
     * opposto di this
     */
    public Poly minus() {
        int[] opposite = new int[poly.length];
        for (int i = 0; i < poly.length; i++) {
            opposite[i] = -poly[i];
        }
        return new Poly(opposite);
    }

    /**
     * Post-condizioni: restituisce il coefficente relativo al grado in input
     */
    public int coeff(int d) {
        if (degree() < d || d < 0)
            return 0;
        return poly[d];
    }

    @Override
    public String toString() {
        String polinomio = "Polinomio: ";
        for (int i = 0; i < poly.length - 1; i++) {
            polinomio += poly[i] + "x^" + i + " + ";
        }
        polinomio += poly[poly.length - 1] + "x^" + (poly.length - 1);
        return polinomio;
    }

}