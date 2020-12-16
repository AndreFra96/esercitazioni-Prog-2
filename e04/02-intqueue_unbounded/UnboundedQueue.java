import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * OVERVIEW: Le instanze di UnboundedQueue sono strutture dati di tipo coda, le
 * UnboundedQueue non sono limitate e sono modificabili.
 * <p>
 * Una coda tipica è [x_1, x_2, ..., x_k], in cui x_k è l'ultimo elemento che è
 * stato inserito nella coda e x_1 è il primo elemento che è stato inserito e il
 * primo che verrà letto.
 * <p>
 * A seguito di una operazione di enqueue la coda sarà [x_1, x_2, ..., x_k,
 * x_k+1], mentre a seguito di una operazione di dequeue la coda sarà [x_2, ...,
 * x_k, x_k].
 * <p>
 * Funzione di astrazione: AF(els) = [els.get(0), els.get(1), ... ,
 * els.get(els.size-1)] se els.size > 0, [] se els.size = 0
 * <p>
 * Invariante di rappresentaziones: la coda non contiene elementi nulli
 */
public class UnboundedQueue {

    /** Struttura dati contentente gli elementi della coda */
    private List<Integer> els;

    /**
     * Post-condizioni: Inizializza this affinchè rappresenti una nuova coda vuota
     * 
     * Preserva l'invariante di rappresentazione poiche rappresenta sempre la
     * UnboundedQueue: [] che è valida per la funzione di astrazione
     */
    public UnboundedQueue() {
        els = new LinkedList<>();
        assert repOk();
    }

    /**
     * Post-condizioni: restituisce la cardinalità di this
     */
    public int size() {
        return els.size();
    }

    /**
     * Effetti collaterali: this è modificato
     * <p>
     * Post-condizioni: Aggiunge l'elemento x alla coda this
     * 
     * Preservo l'invariante di rappresentazione poichè un int non può mai essere
     * null
     */
    public void enqueue(int n) {
        els.add(n);

        assert repOk();
    }

    /**
     * Effetti collaterali: this è modificato se la coda non è vuota
     * 
     * Post-condizioni: Rimuove e restituisce l'elemento in testa alla coda this, se
     * presente. this = [x1, x2, ..., x_k], k < n, this = [x2, ..., x_k]
     * solleva EmptyException se la coda è vuota
     * 
     * Preservo l'invariante di rappresentazione poichè se ho una UnboundedQueue con
     * dimensione diversa da 0 e rimuovo un elemento avrò sempre una
     * UnboundedQueue valida mentre una queue vuota solleva un'eccezzione:
     * 
     * this = [x1,x2, .. , xk], this_post = [x2, ... , xk] se size è maggiore di 0
     * this = [], this_post = [] se size è uguale a 0
     */
    public int dequeue() {
        assert repOk();
        if(size() == 0) throw new EmptyException("la coda è vuota");
        return els.remove(0);
    }

    /** Implementa l'invariante di rappresentazione */
    private boolean repOk() {
        for (int i = 0; i < size(); i++)
            if (els.get(i) == null)
                return false;

        return true;
    }

    /** Implementa la funzione di astrazione */
    @Override
    public String toString() {
        String repr = "IntQueue : [";
        int i;

        for (i = 0; i < size() - 1; i++)
            repr += els.get(i) + ", ";

        if (size() > 0)
            repr += els.get(i);

        return repr += "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UnboundedQueue))
            return false;

        UnboundedQueue queue = (UnboundedQueue) obj;

        if (queue.size() != size())
            return false;

        for (int i = 0; i < size(); i++)
            if (queue.els.get(i) != els.get(i))
                return false;

        return true;
    }
}