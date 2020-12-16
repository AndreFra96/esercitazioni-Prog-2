import java.util.ArrayList;
import java.util.List;
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
 * Invariante di astrazione: ogni monomio all'interno di poly ha grado diverso
 * dagli altri
 */
public class Poly {
    private final List<Monomio> elements;

    /**
     * Post-condizioni: inizializza un nuovo Poly 0
     * 
     * Preservazione RI: preserva l'invariante di rappresentazione poichè costruisce
     * sempre il polinomio zero, ovvero il polinomio composto da un unico monomio di
     * grado zero con coefficente zero, ammesso dall'invariante di rappresentazione
     * 
     * Correttezza: AF(elements) = elements.get(0)x^elements.get(0).degree +
     * elements.get(1)x^elements.get(1).degree + ... + elements.get(elements.size -
     * 1)x^elements.get(elements.size - 1).degree = 0x^0
     */
    public Poly() {
        elements = new ArrayList<>();
        elements.add(new Monomio(0, 0));
        assert repOK();
    }

    /**
     * Post-condizioni: inizializza un nuovo Poly cX^n, solleva
     * NegativeExponentException se n è minore di 0
     * 
     * Preservazione RI: inizializza un nuovo polinomio contente un unico monomio di
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
        elements.add(new Monomio(c, n));
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
    private Poly(List<Monomio> nuoviElementi) {
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
     * coefficente del monomio che ha come grado degree
     */
    public int coeff(int degree) {
        if (degree() < degree)
            throw new IllegalArgumentException("Il Poly ha grado " + degree());
        int coeff = 0;
        for (Monomio actual : elements) {
            if (actual.degree() == degree)
                coeff = actual.coeff();
        }
        return coeff;
    }

    /**
     * Post-condizioni: restituisce un intero corrispondente al grado di this
     * 
     * Correttezza: il grado del polinomio è il grado del monomio con grado più alto
     * all'interno di esso
     */
    public int degree() {
        int degree = 0;
        for (Monomio actual : elements)
            if (actual.degree() > degree)
                degree = actual.degree();
        return degree;
    }

    // E' davvero una copia totale?
    private List<Monomio> getElements() {
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
        List<Monomio> qElements = q.getElements();

        for (Monomio actual : getElements()) {
            boolean foundSimilar = false;
            for (Monomio existent : qElements) {
                if (actual.similar(existent)) {
                    qElements.remove(existent);
                    if (actual.coeff() != (existent.coeff() * -1)) {
                        qElements.add(actual.addSimilar(existent));
                    }
                    foundSimilar = true;
                    break;
                }
            }
            if (!(foundSimilar))
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
        List<Monomio> qElements = q.getElements();
        for (Monomio actual : getElements()) {
            for (Monomio moltiplicatore : qElements) {
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
        List<Monomio> attuale = getElements();
        ArrayList<Monomio> opposto = new ArrayList<>();
        for (Monomio actual : attuale) {
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
        List<Monomio> el = temp.getElements();
        for (Monomio i : elements) {
            for (Monomio j : el) {
                if (i.similar(j) && i.coeff() != j.coeff())
                    return false;
            }
        }
        return true;
    }

    /**
     * OVERVIEW: Le istanze di questa classe rappresentano dei monomi. Gli oggetti
     * di questo tipo sono immutabili. Un Monomio è valido con qualsiasi
     * combinazione di coeff e degree tale che degree sia maggiore o uguale a 0,
     * ogni monomio con coefficente 0 è il monomio nullo ed ha valore 0
     * 
     * Funzione di astrazione: AF(coeff,degree) = coeff*x^degree
     * 
     * Invariante di rappresentazione: degree deve essere maggiore o uguale a 0
     * 
     * Invariante di astrazione: il grado del monomio deve essere maggiore o uguale
     * a 0
     * 
     */
    private class Monomio {
        private final int coeff;
        private final int degree;

        /**
         * Post-condizioni: Inizializza un nuovo monomio con coefficente coeff di grado
         * degree (coeff X^degree), se il coefficente è uguale a zero viene creato in
         * Monomio 0X^degree, il monomio nullo.
         * 
         * Solleva IllegalArgumentException se degree è minore di 0
         * 
         * Preservazione RI: solleva IllegalArgumentException se degree è minore di 0
         * 
         * Correttezza: AF(coeff,degree) = coeff*x^degree
         */
        public Monomio(final int coeff, final int degree) {
            if (degree < 0)
                throw new IllegalArgumentException("Il grado del Monomio deve essere >= 0");
            this.coeff = coeff;
            this.degree = degree;
            assert (repOK());
        }

        /**
         * Post-condizioni: restituisce un intero corrispondente al grado del monomio
         */
        public int degree() {
            return this.degree;
        }

        /**
         * Post-condizioni: restituisce un intero corrispondente al coefficente del
         * monomio
         */
        public int coeff() {
            return this.coeff;
        }

        /**
         * Post-condizioni: restituisce un nuovo monomio corrispondente al monomio
         * opposto di this
         * 
         * Preservazione RI: se this è un monomio valido this_post è sempre un monomio
         * valido perchè il grado non viene modificato
         * 
         * Correttezza: -(coeff*x^degree) = -coeff*x^degree
         */
        public Monomio minus() {
            assert (repOK());
            return new Monomio(coeff() * -1, this.degree);
        }

        /**
         * Post-condizioni: restituisce true se this e match sono monomi dello stesso
         * grado, false altrimenti
         * 
         * solleva NullPointerException se match è null
         * 
         * Correttezza: due monomi si dicono simili quando sono dello stesso grado
         */
        public boolean similar(Monomio match) {
            Objects.requireNonNull(match);
            return this.degree() == match.degree();
        }

        /**
         * Post-condizioni: calcola la somma fra this e m se sono monomi simili
         * 
         * solleva IllegalArgumentException se i due monomi non sono simili
         * 
         * Preservazione RI: se this è un monomio valido quello restituito è sempre un monomio
         * valido perchè il grado non viene modificato
         * 
         * Correttezza: a*x^k + b*x^k = a+b*x^k
         */
        public Monomio addSimilar(Monomio m) {
            assert (repOK());
            if (!(similar(m)))
                throw new IllegalArgumentException("m deve essere simile a this");
            return new Monomio(this.coeff() + m.coeff(), this.degree);
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
            if (!(obj instanceof Monomio))
                return false;
            Monomio temp = (Monomio) obj;
            if (coeff() == 0 && temp.coeff() == 0)
                return true;
            return degree() == temp.degree() && coeff() == temp.coeff();
        }

    }

}