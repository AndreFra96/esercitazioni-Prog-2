import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano dei Polinomi. Gli oggetti
 * di questo tipo sono immutabili
 */
public class Polinomio {
    private final List<Monomio> elements;

    /**
     * Post-condizioni: inizializza un nuovo polinomio 0
     */
    public Polinomio() {
        elements = new ArrayList<>();
        elements.add(new Monomio(0, 0));
    }

    /**
     * Post-condizioni: inizializza un nuovo polinomio: cX^n solleva
     * NegativeExponentException se n è minore di 0
     */
    public Polinomio(int c, int n) {
        if (n < 0)
            throw new NegativeExponentException();
        elements = new ArrayList<>();
        elements.add(new Monomio(c, n));
    }

    /**
     * <p>
     * Pre-condizioni: i monomi contenuti in nuoviElementi devono essere tutti di grado diverso fra loro
     * <p>
     * Effetti-collaterali: potrebbe modificare nuoviElementi, se all'interno della
     * lista sono presenti monomi simili viene conservato solo il primo trovato
     * <p>
     * Post-condizioni: inizializza un nuovo polinomio con i monomi presenti in
     * elementi
     * <p>
     * solleva NullPointerException se la lista in input è null
     */
    private Polinomio(List<Monomio> nuoviElementi) {
        if (nuoviElementi.size() == 0)
            nuoviElementi.add(new Monomio(0, 0));
        this.elements = new ArrayList<>(nuoviElementi);
    }

    /**
     * post-condizioni: restituisce un intero corrispondente al coefficente del
     * polinomio al grado degree
     * <p>
     * solleva IllegalArgumentException se degree è maggiore del grado di this o è
     * minore di 0
     */
    public int coeff(int degree) {
        if (degree() < degree)
            throw new IllegalArgumentException("Il polinomio ha grado " + degree());
        int coeff = 0;
        for (Monomio actual : elements) {
            if (actual.degree() == degree)
                coeff = actual.coeff();
        }
        return coeff;
    }

    /**
     * Post-condizioni: restituisce un intero corrispondente al grado di this
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
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente alla somma di
     * this con il polinomio in input
     * <p>
     * solleva NullPointerException se il Polinomio in input è null
     */
    public Polinomio add(Polinomio q) {
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
        return new Polinomio(qElements);
    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente alla
     * differenza di this con il polinomio in input
     * <p>
     * solleva NullPointerException se il Polinomio in input è null
     */
    public Polinomio sub(Polinomio q) {
        Objects.requireNonNull(q);
        return add(q.minus());
    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente al prodotto di
     * this con il polinomio in input
     * <p>
     * solleva NullPointerException se il Polinomio in input è null
     */
    public Polinomio mul(Polinomio q) {
        Polinomio result = new Polinomio(0, 0);
        List<Monomio> qElements = q.getElements();
        for (Monomio actual : getElements()) {
            for (Monomio moltiplicatore : qElements) {
                result = result.add(new Polinomio(actual.coeff() * moltiplicatore.coeff(),
                        actual.degree() + moltiplicatore.degree()));
            }
        }
        return result;
    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente al polinomio
     * opposto a this
     */
    public Polinomio minus() {
        List<Monomio> attuale = getElements();
        ArrayList<Monomio> opposto = new ArrayList<>();
        for (Monomio actual : attuale) {
            opposto.add(actual.minus());
        }
        return new Polinomio(opposto);
    }

    @Override
    public String toString() {
        String returnString = "Polinomio: {";
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
     * OVERVIEW: Le istanze di questa classe rappresentano dei monomi. Gli oggetti
     * di questo tipo sono immutabili. Un Monomio è valido con qualsiasi
     * combinazione di coeff e degree tale che degree sia maggiore o uguale a 0,
     * ogni monomio con coefficente 0 è il monomio nullo ed ha valore 0
     */
    private class Monomio {
        private final int coeff;
        private final int degree;

        /**
         * Post-condizioni: Inizializza un nuovo monomio con coefficente coeff di grado
         * degree (coeff X^degree), se il coefficente è uguale a zero viene creato in
         * Monomio 0X^degree, il monomio nullo.
         * <p>
         * Solleva IllegalArgumentException se degree è minore di 0
         */
        public Monomio(final int coeff, final int degree) {
            if (degree < 0)
                throw new IllegalArgumentException("Il grado del Monomio deve essere >= 0");
            this.coeff = coeff;
            this.degree = degree;
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
         */
        public Monomio minus() {
            return new Monomio(coeff() * -1, this.degree);
        }

        /**
         * Post-condizioni: restituisce true se this e match sono monomi dello stesso
         * grado, false altrimenti
         * <p>
         * solleva NullPointerException se match è null
         */
        public boolean similar(Monomio match) {
            Objects.requireNonNull(match);
            return this.degree() == match.degree();
        }

        /**
         * Post-condizioni: calcola la somma fra this e m se sono monomi simili
         * <p>
         * solleva IllegalArgumentException se i due monomi non sono simili
         */
        public Monomio addSimilar(Monomio m) {
            if (!(similar(m)))
                throw new IllegalArgumentException("m deve essere simile a this");
            return new Monomio(this.coeff() + m.coeff(), this.degree);
        }

        @Override
        public String toString() {
            return this.coeff() + "x^" + this.degree();
        }

    }

    /**
     * Post-condizioni: restituisce un nuovo Polinomio corrispondente alla somma di
     * this con il polinomio in input
     * <p>
     * solleva NullPointerException se il Polinomio in input è null
     * <p>
     * TODO: DA RIVEDERE!
     */
    // public Polinomio addOttimizzato(Polinomio q){
    // List<Monomio> polinomioAlto;
    // List<Monomio> polinomioBasso;
    // Polinomio alto;

    // if(this.degree() > q.degree()){
    // polinomioAlto = this.getElements();
    // polinomioBasso = this.getElements();
    // alto = this;
    // }else{
    // polinomioAlto = q.getElements();
    // polinomioBasso = this.getElements();
    // alto = q;
    // }

    // for(Monomio nuovo : polinomioBasso){
    // if(alto.coeff(nuovo.degree()) == 0){
    // polinomioAlto.add(nuovo);
    // }else{
    // polinomioAlto.remove(nuovo);
    // polinomioAlto.add(new Monomio(alto.coeff(nuovo.degree()) +
    // nuovo.coeff(),nuovo.degree()));
    // }
    // }

    // return new Polinomio(polinomioAlto);
    // }
}