import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano dei polinomi, Poly è
 * senza limiti ed immutabile un tipico Poly è [2,14,19] -> 2x^0 + 14x + 19 x^2,
 * [0] -> 0x^0
 * <p>
 * Funzione di astrazione: AF(poly) = poly[0]x^0 + poly[1]x^1 + ... +
 * poly[poly.size]x^poly.size
 * <p>
 * Invariante di rappresentazione: this.poly diverso da null, this.size maggiore
 * di 0, se poly.size > 1 allora poly[poly.size] != 0
 * <p>
 * Invariante di astrazione: se il grado del polinomio è diverso da 0, il
 * coefficente di grado massimo del polinomio deve essere diverso da zero
 */
public class Poly {
    private final int[] poly;

    /**
     * Post-condizioni: restituisce un Iteratore sui gradi del polinomio con
     * coefficente diverso da 0, ordinati in ordine crescente
     */
    public Iterator<Integer> degrees() {
        return new PolyDegreesGenerator(this);
    }

    /**
     * GENERATORE
     */
    static class PolyDegreesGenerator implements Iterator<Integer> {
        private Poly myPoly;
        private int index;

        PolyDegreesGenerator(Poly p) {
            this.myPoly = p;
            index = -1;
        }

        @Override
        public boolean hasNext() {
            // Per RI sono sicuro di avere almeno il coefficente di grado massimo diverso da
            // 0
            return index < myPoly.degree();
        }

        @Override
        public Integer next() {
            if (!hasNext())
                throw new NoSuchElementException();
            index++;
            return myPoly.coeff(index) == 0 ? next() : index;
        }
    }

    /**
     * Post-condizioni: Inizializza un nuovo polinomio 0, il polinomio 0 è il
     * polinomio 0x^0
     * 
     * Preservazione RI: inizializza poly a [0] che corrisponde al polinomio zero,
     * ovvero al polinomio di dimensione 1 che può avere come coefficente 0
     * 
     * Correttezza: AF(poly) = poly[0]x^0 + poly[1]x^1 + ... +
     * poly[poly.size]x^poly.size = 0x^0;
     * 
     */
    public Poly() {
        poly = new int[1];
        assert repOk();
    }

    /**
     * Post-condizioni: Inizializza un nuovo polinomio di grado n (cx^n), se c è
     * uguale a 0 inizializzo un polinomio zero
     * <p>
     * solleva NegativeExponentException se n è minore di 0
     * 
     * Preservazione RI: se n è minore di 0 viene sollevata un'eccezione, altrimenti
     * this.size = n+1
     * 
     * Correttezza: AF(poly) = poly[0]x^0 + poly[1]x^1 + ... +
     * poly[poly.size]x^poly.size = 0x^0 + 0x^1 + ... + cx^n
     */
    public Poly(int c, int n) {
        if (n < 0)
            throw new NegativeExponentException();
        poly = new int[n + 1];
        poly[n] = c;
        assert repOk();

    }

    /**
     * Post-condizioni: inizializza un nuovo polinomio attraverso l'array in input
     * solleva NullPointerException se p è null, solleva IllegalArgumentException se
     * p ha dimensione 0 o l'ultimo elemento di p è 0
     * 
     * Preservazione RI: se p è null viene sollevata una NullPointerException, se p
     * ha dimensione 0 o l'ultimo elemento è 0 viene sollevata una
     * IllegalArgumentException, altrimenti p può essere utilizzato per
     * rappresentare un polinomio valido
     * 
     */
    private Poly(int[] p) {
        Objects.requireNonNull(p);

        if (p.length == 0)
            throw new IllegalArgumentException("p non rappresenta un polinomio valido");
        else if (p.length != 1 && p[p.length - 1] == 0)
            throw new IllegalArgumentException("p non rappresenta un polinomio valido");

        this.poly = p;
        assert repOk();

    }

    /**
     * Post-condizioni: Restituisce un array di interi corrispondente agli elementi
     * di poly ordinati
     * 
     * Correttezza: se this è un polinomio valido gli elementi di this sono tutti
     * gli elementi di poly
     */
    private int[] getElements() {
        return Arrays.copyOf(poly, poly.length);
    }

    /**
     * Post-condizioni: restituisce un intero corrispondente al grado del polinomio
     * attuale
     * 
     * Correttezza: se this è un polinomio valido allora il grado del polinomio
     * corrisponde alla dimensione di poly -1
     */
    public int degree() {
        return poly.length - 1;
    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente alla somma di
     * this e q
     * <p>
     * solleva NullPointerException se q è null
     * 
     * Correttezza: (this.poly[0]x^0 + this.poly[1]x^1 + ... +
     * this.poly[this.poly.length - 1]x^this.poly.length - 1) + (q.poly[0]x^0 +
     * q.poly[1]x^1 + ... + q.poly[q.poly.length - 1]x^q.poly.length - 1) è uguale
     * al polinomio il quale coefficente di grado x è uguale al coefficente di grado
     * x di this (o 0 se il this ha grado inferiore a x) sommato al coefficente del
     * grado x di q (o 0 se il q ha grado inferiore a x) per ogni x compresa fra 0 e
     * il grado del polinomio di grado maggiore.
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
     * 
     * Correttezza: (this.poly[0]x^0 + this.poly[1]x^1 + ... +
     * this.poly[this.poly.length - 1]x^this.poly.length - 1) - (q.poly[0]x^0 +
     * q.poly[1]x^1 + ... + q.poly[q.poly.length - 1]x^q.poly.length - 1) =
     * (this.poly[0]x^0 + this.poly[1]x^1 + ... + this.poly[this.poly.length -
     * 1]x^this.poly.length - 1) + (- (q.poly[0]x^0 + q.poly[1]x^1 + ... +
     * q.poly[q.poly.length - 1]x^q.poly.length - 1))
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
     * 
     * Correttezza: (this.poly[0]x^0 + this.poly[1]x^1 + ... +
     * this.poly[this.poly.length - 1]x^this.poly.length - 1) * (q.poly[0]x^0 +
     * q.poly[1]x^1 + ... + q.poly[q.poly.length - 1]x^q.poly.length - 1) è uguale
     * alla somma dei prodotti parziali fra i termini dei polinomi
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
     * 
     * Correttezza: -(this.poly[0]x^0 + this.poly[1]x^1 + ... +
     * this.poly[this.poly.length - 1]x^this.poly.length - 1) = -this.poly[0]x^0 -
     * this.poly[1]x^1 - ... - this.poly[this.poly.length - 1]x^this.poly.length - 1
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Poly))
            return false;
        Poly compare = (Poly) obj;
        if (compare.degree() != degree())
            return false;
        for (int i = 0; i <= degree(); i++)
            if (compare.coeff(i) != coeff(i))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = Integer.hashCode(degree());
        for (int i = 0; i <= degree(); i++)
            hash = 31 * hash + Integer.hashCode(coeff(i));
        return hash;
    }

    public boolean repOk() {
        if (this.poly == null)
            return false;
        if (this.poly.length != 1 && this.poly[poly.length - 1] == 0)
            return false;
        return true;
    }

}