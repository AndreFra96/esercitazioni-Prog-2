import java.util.Objects;

/**
 * OVERVIEW:Le istanze di questa classe rappresentano numeri razionali. Gli
 * oggetti di questa classe non sono mutabili. Un numero razionale tipico è n/d
 * dove n e d sono numeri interi
 * 
 * Funzione di astrazione: AF(num,den) = numero razionale num/den
 * 
 * Invariante di rappresentazione: den > 0
 * 
 * Invariante di astrazione: den > 0, il razionale è sempre rappresentato con
 * segno al numeratore e ridotto ai minimi termini
 */
public class Razionale {

    /** numeratore del razionale */
    private int num;
    /** denominatore del razionale */
    private int den;

    /**
     * Post-condizioni: inizializza un nuovo razionale con numeratore num e
     * denominatore den solleva ZeroDivisorException se den è 0
     * 
     * Preservazione RI: se den == 0, è sollevata un'accezione e quindi this non è
     * istanziato, altrimenti se den è negativo viene spostato il segno al
     * numeratore quindi den è sempre maggiore di 0
     * 
     * Correttezza: AF(num,den) = num/den = (num/cd)/(den/cd), dove cd =
     * mcd(|num|,den) = -|num| / den, se den < 0 = num / den, se den > 0
     * 
     */
    public Razionale(int n, int d) {
        if (d == 0)
            throw new ZeroDivisorException();

        this.num = n * d > 0 ? Math.abs(n) : -Math.abs(n);
        this.den = Math.abs(d);

        reduce();
        assert repOk();
    }

    /**
     * Post-condizioni: inizializza e restituisce un nuovo razionale corrispondente
     * alle somma di this e s, solleva NullPointerException se s è null
     * 
     * this = x/y, s = z/w -> return = (x/y) + (z/w)
     * 
     * Preservazione RI: den > 0 & s.den > 0 -> den * s.den è maggiore di 0
     * 
     * Correttezza: num/den + s.num/s.den = s.den * den + s.num * num, den * s.den
     */
    public Razionale add(Razionale s) {
        assert repOk();

        Objects.requireNonNull(s);
        return new Razionale(s.den * num + s.num * den, den * s.den);

    }

    /**
     * Post-condizioni: inizializza e restituisce un nuovo razionale corrispondente
     * alla differenza fra this e s, solleva NullPointerException se s è null
     * 
     * Preservazione RI: den > 0 & s.den > 0 -> den * s.den è maggiore di 0
     * 
     * Correttezza: num/den + s.num/s.den = s.den * den + s.num * num / den * s.den
     */
    public Razionale sub(Razionale s) {
        assert s.repOk();
        Objects.requireNonNull(s);
        s.reduce();
        return add(s);
    }

    /**
     * Post-condizioni: inizializza e restituisce un nuovo razionale corrispondente
     * al prodotto fra this e s, solleva NullPointerException se s è null
     * 
     * Preservazione RI: den > 0 & s.den > 0 -> den * s.den è maggiore di 0
     * 
     * Correttezza: num/den * s.num/s.den = num * s.num / den * s.den
     */
    public Razionale mul(Razionale s) {
        assert s.repOk();
        Objects.requireNonNull(s);
        return new Razionale(num * s.num,den * s.den);
    }

    /**
     * Post-condizioni: inizializza e restituisce un nuovo razionale corrispondente
     * alla prodotto della divisione fra this e s, solleva NullPointerException se s
     * è null, solleva ZeroDivisorException se il numeratore di s è 0
     * <p>
     * this = x/y, s = z/w -> return = xw/yz
     * <p>
     * Preservazione RI: se s.num == 0 solleva un'eccezione, altrimenti den è sempre maggiore di 0
     * 
     * Correttezza: (num/den) / (s.num / s.den), se s.num è diverso da 0 = (num/den) * (s.den / s.num) 
     */
    public Razionale div(Razionale s) {
        assert s.repOk();

        Objects.requireNonNull(s);
        if (s.num == 0)
            throw new ZeroDivisorException();
        return mul(s.reciproco());
    }

    /**
     * Post-condizioni: inizializza e restituisce un nuovo polinomio corrispondende
     * al reciproco di this
     * 
     * Preservazione RI: num == 0 -> viene sollevata un'eccezione
     * 
     * Correttezza: num != 0 -> num ^ -1 = den/num
     */
    public Razionale reciproco() {
        if (num == 0)
            throw new ZeroDivisorException();
        return new Razionale(den, num);
    }

    /**
     * Post-condizioni: inizializza e restituisce un nuovo Razionale corrispondente
     * a this ridotto ai minimi termini
     * <p>
     * 
     * Preservazione RI: dato che den è maggiore di 0 e maggiore di mcd(|num|,den)
     * allora den / mcd(|num|,den) non può che essere maggiore di 0
     * 
     * Correttezza: AF(num,den) = num/den = (num/cd)/(den/cd), dove cd =
     * mcd(|num|,den)
     * 
     */
    private void reduce() {

        int num = this.num;
        int den = this.den;
        int mcd = mcd(num, den);
        this.num = num / mcd;
        this.den = den / mcd;

        assert repOk();

    }

    /**
     * Post-condizioni: restituisce un intero corrispondente al massimo comun
     * divisore di x e y
     */
    private static int mcd(int x, int y) {

        // Ordino x e y in modo tale che x sia maggiore di y
        if (y > x) {
            int temp = x;
            x = y;
            y = temp;
        }

        // Algoritmo euclideo per il calcolo MCD
        int r;
        while (x != 0) {
            r = y % x;
            y = x;
            x = r;
        }
        return y;
    }

    @Override
    public String toString() {
        return den == 1 ? num+"" : num + "/" + den;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Razionale))
            return false;
        Razionale match = (Razionale) obj;
        return num == match.num && den == match.den;
    }

    @Override
    public int hashCode() {
        int result;

        result = Integer.hashCode(num);
        result = 31 * result + Integer.hashCode(den);

        return result;
    }

    /**
     * Implementa l'invariante di rappresentazione
     * <p>
     * Post-condizioni: restituisce true se this rispetta l'invariante di
     * rappresentazione, false altrimenti
     */
    private boolean repOk() {
        return den != 0;
    }

}