import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * OVERVIEW: Le instanze di UnboundedQueue sono strutture dati di tipo coda, le
 * UnboundedQueue non sono limitate e sono modificabili.
 * 
 * Una coda tipica è [x_1, x_2, ..., x_k], in cui x_k è l'ultimo elemento che è
 * stato inserito nella coda e x_1 è il primo elemento che è stato inserito e il
 * primo che verrà letto.
 * 
 * A seguito di una operazione di enqueue la coda sarà [x_1, x_2, ..., x_k,
 * x_k+1], mentre a seguito di una operazione di dequeue la coda sarà [x_2, ...,
 * x_k, x_k].
 * 
 * Funzione di astrazione: AF(els) = [els.get(0), els.get(1), ... ,
 * els.get(els.size-1)] se els.size > 0, [] se els.size = 0
 * 
 * Invariante di rappresentazione: els != null, ogni elemento di els != null
 * 
 * Invariante di astrazione: la coda non è nulla, ne contiene elementi nulli
 */
public class UnboundedQueue {

    /** Struttura dati contentente gli elementi della coda */
    private List<Integer> els;

    /**
     * Post-condizioni: Inizializza this affinchè rappresenti una nuova coda vuota
     * 
     * Preservazione RI: preserva l'invariante di rappresentazione poiche
     * rappresenta sempre la UnboundedQueue: [] che è diversa da null e non contiene
     * elementi null
     * 
     * Correttezza: AF(els) = [] se els.size = 0
     */
    public UnboundedQueue() {
        els = new LinkedList<>();
        assert repOk();
    }

    /** Iteratore sugli elementi di this */
    public Iterator<Integer> elements() {
        return new QueueElementsGenerator(this);
    }

    /**GENERATORE */
    static class QueueElementsGenerator implements Iterator<Integer> {
        UnboundedQueue q;
        int index;

        QueueElementsGenerator(UnboundedQueue queue) {
            this.q = queue;
        }

        @Override
        public boolean hasNext() {
            return index < q.size();
        }

        @Override
        public Integer next() {
            return q.els.get(index++);
        }
    }

    /**
     * Post-condizioni: restituisce la cardinalità di this
     * 
     * Correttezza: dato che ogni elmento di els è un elemento della coda, la
     * dimensione della coda è la dimensione di els
     */
    public int size() {
        return els.size();
    }

    /**
     * Effetti collaterali: this è modificato
     * 
     * Post-condizioni: Aggiunge l'elemento x alla coda this
     * 
     * Preservazione RI: preserva l'invariante di rappresentazione poichè un int non
     * può mai essere null
     * 
     * Correttezza: this = [x_1, x_2, ..., x_k] => this_post = [x_1, x_2, ...,
     * x_k,n]
     */
    public void enqueue(int n) {
        els.add(n);

        assert repOk();
    }

    /**
     * Effetti collaterali: this è modificato se la coda non è vuota
     * 
     * Post-condizioni: Rimuove e restituisce l'elemento in testa alla coda this, se
     * presente. this = [x1, x2, ..., x_k], k < n, this = [x2, ..., x_k] solleva
     * EmptyException se la coda è vuota
     * 
     * Preservazione RI: preserva l'invariante di rappresentazione poichè se ho una
     * UnboundedQueue con dimensione diversa da 0 e rimuovo un elemento avrò sempre
     * una UnboundedQueue valida mentre una queue vuota solleva un'eccezione: this =
     * [x1,x2, .. , xk], this_post = [x2, ... , xk] se size è maggiore di 0 this =
     * [], this_post = [] se size è uguale a 0
     * 
     * Correttezza: this = [x_1, x_2, ..., x_k] => this_post = [x_2, ..., x_k] se
     * size è maggiore di 0, altrimenti solleva EmptyException
     */
    public int dequeue() {
        assert repOk();
        if (size() == 0)
            throw new EmptyException("la coda è vuota");
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