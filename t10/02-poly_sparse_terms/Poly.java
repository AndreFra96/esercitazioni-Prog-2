import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano dei Polinomi. Gli oggetti
 * di questo tipo sono immutabili
 * 
 * 
 * Funzione di astrazione: AF(elements) =
 * elements.get(0)x^elements.get(0).degree +
 * elements.get(1)x^elements.get(1).degree + ... + elements.get(elements.size -
 * 1)x^elements.get(elements.size - 1).degree
 * 
 * Invariante di rappresentazione: elements è diverso da null ogni elemento di
 * elements è diverso da null ogni elemento di elements ha grado diverso
 * rispetto a tutti gli altri elmenti di elements
 * 
 * Invariante di astrazione: ogni Term all'interno di poly ha grado diverso
 * dagli altri
 */
public class Poly {
    private final List<Term> elements;

    /**
     * Post-condizioni: restituisce un Iteratore sui termini del polinomio in ordine
     * crescente di grado
     */
    public Iterator<Poly.Term> terms() {
        return new TermsGenerator(this);
    }

    static class TermsGenerator implements Iterator<Poly.Term> {
        private Poly p;
        private int index;

        TermsGenerator(Poly p) {
            this.p = p.sortByDegree();
        }

        @Override
        public boolean hasNext() {
            return index < p.elements.size();
        }

        @Override
        public Term next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Term next = p.elements.get(index);
            index++;
            return next;
        }
    }

    /**
     * Post-condizioni: inizializza un nuovo Poly 0
     * 
     * Preservazione RI: preserva l'invariante di rappresentazione poichè costruisce
     * sempre il polinomio zero, ovvero il polinomio composto da un unico Term di
     * grado zero con coefficente zero, ammesso dall'invariante di rappresentazione
     * 
     * Correttezza: AF(elements) = elements.get(0)x^elements.get(0).degree +
     * elements.get(1)x^elements.get(1).degree + ... + elements.get(elements.size -
     * 1)x^elements.get(elements.size - 1).degree = 0x^0
     */
    public Poly() {
        elements = new ArrayList<>();
        elements.add(new Term(0, 0));
        assert repOK();
    }

    /**
     * Post-condizioni: inizializza un nuovo Poly cX^n, solleva
     * NegativeExponentException se n è minore di 0
     * 
     * Preservazione RI: inizializza un nuovo polinomio contente un unico Term di
     * grado n con coefficente c, se n è minore di 0 viene sollevata una
     * NegativeExponentException
     * 
     * Correttezza: AF(elements) = elements.get(0)x^elements.get(0).degree +
     * elements.get(1)x^elements.get(1).degree + ... + elements.get(elements.size -
     * 1)x^elements.get(elements.size - 1).degree = cx^n
     */
    public Poly(int c, int n) {
        if (n < 0)
            throw new NegativeExponentException();
        elements = new ArrayList<>();
        elements.add(new Term(c, n));
        assert repOK();
    }

    /**
     * 
     * Pre-condizioni: i monomi contenuti in nuoviElementi devono essere tutti di
     * grado diverso fra loro e diversi da null
     * 
     * Effetti-collaterali: potrebbe modificare nuoviElementi, se all'interno della
     * lista sono presenti monomi simili viene conservato solo il primo trovato
     * 
     * Post-condizioni: inizializza un nuovo Poly con i monomi presenti in elementi,
     * solleva NullPointerException se la lista in input è null, solleva
     * IllegalArgumentException se nuoviElementi ha dimensione 0
     * 
     * Preservazione RI: se nuoviElementi è null solleva un'eccezione, altrimenti se
     * nuoviElementi rispetta le precondizioni il polinomio costruito rispetta
     * l'invariante di rappresentazione
     * 
     */
    private Poly(List<Term> nuoviElementi) {
        Objects.requireNonNull(nuoviElementi);

        if (nuoviElementi.size() == 0)
            throw new IllegalArgumentException("nuoviElementi deve contenere almeno 1 elemento");

        this.elements = new ArrayList<>(nuoviElementi);
        assert repOK();
    }

    /**
     * post-condizioni: restituisce un intero corrispondente al coefficente del Poly
     * al grado degree, solleva IllegalArgumentException se degree è maggiore del
     * grado di this o è minore di 0
     * 
     * Correttezza: se degree è maggiore del grado del polinomio solleva
     * un'eccezione, altrimenti il coefficente del polinomio di grado degree è il
     * coefficente del Term che ha come grado degree
     */
    public int coeff(int degree) {
        if (degree() < degree)
            throw new IllegalArgumentException("Il Poly ha grado " + degree());
        int coeff = 0;
        for (Term actual : elements) {
            if (actual.degree() == degree)
                coeff = actual.coeff();
        }
        return coeff;
    }

    /**
     * Post-condizioni: restituisce un nuovo Poly uguale a this nel quale gli
     * elementi sono ordinati per grado crescente e non esistono monomi con
     * coefficente 0
     * 
     * Preservazione RI: se this è un Poly valido allora qualunque sia la posizione
     * nella quale si trovano i monomi il Poly è valido
     * 
     */
    public Poly sortByDegree() {
        List<Term> l = new ArrayList<Term>();
        for (int i = 0; i <= degree(); i++) {
            if (coeff(i) > 0) {
                l.add(new Term(coeff(i), i));
            }
        }
        return new Poly(l);
    }

    /**
     * Post-condizioni: restituisce un intero corrispondente al grado di this
     * 
     * Correttezza: il grado del polinomio è il grado del Term con grado più alto
     * all'interno di esso
     */
    public int degree() {
        int degree = 0;
        for (Term actual : elements)
            if (actual.degree() > degree)
                degree = actual.degree();
        return degree;
    }

    // E' davvero una copia totale?
    private List<Term> getElements() {
        return new ArrayList<>(elements);
    }

    /**
     * Post-condizioni: restituisce un nuovo Poly corrispondente alla somma di this
     * con il Poly in input, solleva NullPointerException se il Poly in input è null
     * 
     * Preservazione RI: se q è null solleva NullPointerException, altrimenti
     * restituisce un nuovo polinomio contenente tutti i monomi di this e q che
     * hanno grado diverso, e nuovi monomi corrispondenti alla somma dei due
     * coefficenti se i monomi hanno grado uguale
     * 
     */
    public Poly add(Poly q) {
        assert repOK();
        Objects.requireNonNull(q);
        List<Term> qElements = q.getElements();

        for (Term actual : getElements()) {
            boolean foundSimilar = false;
            for (Term existent : qElements) {
                if (actual.similar(existent)) {
                    qElements.remove(existent);
                    if (actual.coeff() != (existent.coeff() * -1)) {
                        qElements.add(actual.addSimilar(existent));
                    }
                    foundSimilar = true;
                    break;
                }
            }
            if (!(foundSimilar) && actual.coeff() != 0)
                qElements.add(actual);
        }
        return new Poly(qElements);
    }

    /**
     * Post-condizioni: restituisce un nuovo Poly corrispondente alla differenza di
     * this con il Poly in input, solleva NullPointerException se il Poly in input è
     * null
     * 
     * Preservazione RI: se q è null solleva NullPointerException, altrimenti esegue
     * la somma per il reciproco di q e sia somma che reciproco mantengono sempre
     * l'invariante di rappresentazione valido
     * 
     * Correttezza: (ax^b + cx^d) - (ex^f + gx^h) = (ax^b + cx^d) + (-ex^f - gx^h)
     */
    public Poly sub(Poly q) {
        Objects.requireNonNull(q);
        return add(q.minus());
    }

    /**
     * Post-condizioni: restituisce un nuovo Poly corrispondente al prodotto di this
     * con il Poly in input, solleva NullPointerException se il Poly in input è null
     * 
     * Preservazione RI: se q è null solleva NullPointerException, altrimenti
     * vengono eseguite una serie di somme, operazione che mantiene valida
     * l'invariante di rappresentazione
     * 
     * Correttezza: la moltiplicazione è calcolata come somma dei prodotti parziali
     * fra i monomi dei due polinomi
     */
    public Poly mul(Poly q) {
        Objects.requireNonNull(q);
        Poly result = new Poly(0, 0);
        List<Term> qElements = q.getElements();
        for (Term actual : getElements()) {
            for (Term moltiplicatore : qElements) {
                result = result.add(
                        new Poly(actual.coeff() * moltiplicatore.coeff(), actual.degree() + moltiplicatore.degree()));
            }
        }
        return result;
    }

    /**
     * Post-condizioni: restituisce un nuovo Poly corrispondente al Poly opposto a
     * this
     * 
     * Preservazione RI: effettuando il cambio dei segni del coefficente dei monomi
     * del polinomio non è possibile invalidare l'invariante di rappresentazione
     * 
     * Correttezza: -(elements.get(0)x^elements.get(0).degree +
     * elements.get(1)x^elements.get(1).degree + ... + elements.get(elements.size -
     * 1)x^elements.get(elements.size - 1).degree) =
     * -elements.get(0)x^elements.get(0).degree -
     * elements.get(1)x^elements.get(1).degree - ... - elements.get(elements.size -
     * 1)x^elements.get(elements.size - 1).degree
     */
    public Poly minus() {
        List<Term> attuale = getElements();
        ArrayList<Term> opposto = new ArrayList<>();
        for (Term actual : attuale) {
            opposto.add(actual.minus());
        }
        return new Poly(opposto);
    }

    @Override
    public String toString() {
        String returnString = "Poly: {";
        for (int i = 0; i < elements.size() - 1; i++) {
            returnString += elements.get(i) + " + ";
        }
        if (elements.size() > 0) {
            returnString += elements.get(elements.size() - 1);
        }
        returnString += "} grado = " + this.degree();
        return returnString;
    }

    /**
     * Implementa l'invariante di rappresentazione Post-condizioni: restituisce true
     * se l'invariante di rappresentazione è rispettato, false altrimenti
     */
    private boolean repOK() {
        if (elements == null)
            return false;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i) == null)
                return false;
            for (int j = 0; j < elements.size(); j++) {
                if (i != j && elements.get(i).degree() == elements.get(j).degree())
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Poly))
            return false;
        Poly temp = (Poly) obj;
        if (temp.degree() != degree())
            return false;
        List<Term> el = temp.getElements();
        for (Term i : elements) {
            for (Term j : el) {
                if (i.similar(j) && i.coeff() != j.coeff())
                    return false;
            }
        }
        return true;
    }

    /**
     * OVERVIEW: Le istanze di questa classe rappresentano dei monomi. Gli oggetti
     * di questo tipo sono immutabili. Un Term è valido con qualsiasi
     * combinazione di coeff e degree tale che degree sia maggiore o uguale a 0,
     * ogni Term con coefficente 0 è il Term nullo ed ha valore 0
     * 
     * Funzione di astrazione: AF(coeff,degree) = coeff*x^degree
     * 
     * Invariante di rappresentazione: degree deve essere maggiore o uguale a 0
     * 
     * Invariante di astrazione: il grado del Term deve essere maggiore o uguale
     * a 0
     * 
     */
    static class Term {
        private final int coeff;
        private final int degree;

        /**
         * Post-condizioni: Inizializza un nuovo Term con coefficente coeff di grado
         * degree (coeff X^degree), se il coefficente è uguale a zero viene creato in
         * Term 0X^degree, il Term nullo.
         * 
         * Solleva IllegalArgumentException se degree è minore di 0
         * 
         * Preservazione RI: solleva IllegalArgumentException se degree è minore di 0
         * 
         * Correttezza: AF(coeff,degree) = coeff*x^degree
         */
        public Term(final int coeff, final int degree) {
            if (degree < 0)
                throw new IllegalArgumentException("Il grado del Term deve essere >= 0");
            this.coeff = coeff;
            this.degree = degree;
            assert (repOK());
        }

        /**
         * Post-condizioni: restituisce un intero corrispondente al grado del Term
         */
        public int degree() {
            return this.degree;
        }

        /**
         * Post-condizioni: restituisce un intero corrispondente al coefficente del
         * Term
         */
        public int coeff() {
            return this.coeff;
        }

        /**
         * Post-condizioni: restituisce un nuovo Term corrispondente al Term
         * opposto di this
         * 
         * Preservazione RI: se this è un Term valido this_post è sempre un Term
         * valido perchè il grado non viene modificato
         * 
         * Correttezza: -(coeff*x^degree) = -coeff*x^degree
         */
        public Term minus() {
            assert (repOK());
            return new Term(coeff() * -1, this.degree);
        }

        /**
         * Post-condizioni: restituisce true se this e match sono monomi dello stesso
         * grado, false altrimenti
         * 
         * solleva NullPointerException se match è null
         * 
         * Correttezza: due monomi si dicono simili quando sono dello stesso grado
         */
        public boolean similar(Term match) {
            Objects.requireNonNull(match);
            return this.degree() == match.degree();
        }

        /**
         * Post-condizioni: calcola la somma fra this e m se sono monomi simili
         * 
         * solleva IllegalArgumentException se i due monomi non sono simili
         * 
         * Preservazione RI: se this è un Term valido quello restituito è sempre un
         * Term valido perchè il grado non viene modificato
         * 
         * Correttezza: a*x^k + b*x^k = a+b*x^k
         */
        public Term addSimilar(Term m) {
            assert (repOK());
            if (!(similar(m)))
                throw new IllegalArgumentException("m deve essere simile a this");
            return new Term(this.coeff() + m.coeff(), this.degree);
        }

        @Override
        public String toString() {
            return this.coeff() + "x^" + this.degree();
        }

        /**
         * Implementa l'invariante di rappresentazione Post-condizioni: restituisce true
         * se l'invariante di rappresentazione è rispettato, false altrimenti
         */
        private boolean repOK() {
            return degree() >= 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Term))
                return false;
            Term temp = (Term) obj;
            if (coeff() == 0 && temp.coeff() == 0)
                return true;
            return degree() == temp.degree() && coeff() == temp.coeff();
        }

    }

}