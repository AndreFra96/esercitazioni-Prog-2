/**
 * OVERVIEW: Le istanze di questa classe rappresentano code (limitate) di
 * interi. Gli oggetti di questo tipo sono mutabili. Una coda tipica è [x_1,
 * x_2, ..., x_k], in cui k è minore della capienza della coda. Dato che è una
 * struttura dati che opera in modalità FIFO, a seguito di una operazione di
 * enqueue la coda sarà [x_1, x_2, ..., x_k, x_k+1], mentre a seguito di una
 * operazione di dequeue la coda sarà [x_2, ..., x_k, x_k].
 * 
 */
public class IntQueue {
    // Campi
    /** La struttura dati contenente gli elementi dell'IntQueue this. */
    private final int[] elements;

    /**
     * Gli indici della testa e della coda della IntQueue. Nello specifico, head
     * indica l'indice del primo elemento di this (-1 se la coda è vuota), mentre
     * tail indica l'indice della prima posizione disponibile di this (head = tail
     * se la coda è piena).
     */
    private int head, tail;

    /**
     * ABS FUN: AF(elements, head, tail) = [ elements[i] | head <= i < tail ] =
     * [elements[head], elements[head+1], ..., elements[tail-1]] se -1 < head <=
     * tail o [elements[head], elements[head+1], ..., elements[elements.size-1],
     * elements[0], ..., elements[tail-1]] se head > tail
     *
     * REP INV: la coda non contiene più elementi della sua capienza massima, -1 <=
     * head < size, 0 <= tail < size, head == -1 ⇒ tail = 0
     * 
     * ABS INV: il numero massimo di elementi contenuto nella coda è esattamente
     * uguale alla dimensione di quest'ultima, la coda vuota è quella con head in
     * posizione -1 e tail = 0
     */

    // Costruttori
    /**
     * Post-condizioni: Inizializza this affinché rappresenti una coda vuota con
     * dimensione massima n. Solleva un'eccezione di tipo NegativeArraySizeException
     * se n è negativo.
     * 
     * Preservazione RI: la coda viene inizializzata con dimensione n, head è a -1 e
     * tail = 0 ciò significa che sto rappresentando la coda vuota senza alcun
     * elemento al suo interno la quale può avere qualsiasi dimensione maggiore o
     * uguale a 0, se n è minore di 0 viene sollevata un'eccezione
     * 
     * Correttezza: se n è negativo solleva NegativeArraySizeException altrimenti
     * inizializza sempre la coda vuota di dimensione n = [ elements[i] | head <= i
     * < tail ] = [elements[head], elements[head+1], ..., elements[tail-1]] dato che
     * head è -1 e tail è 0
     */
    public IntQueue(int n) {
        elements = new int[n];
        head = -1;
        tail = 0;

        assert repOK();
    }

    // Metodi
    /**
     * Effetti collaterali: this è modificato se la coda non è piena
     * 
     * Post-condizioni: Aggiunge l'elemento x alla coda this e solleva un'eccezione
     * di tipo FullException se la coda è piena this_post = this + [x]
     * 
     * Preservazione RI: se la coda è piena viene sollevata un'eccezione altrimenti
     * se la coda è vuota head viene inizializzato a 0 oppure non viene modificato
     * quindi è sempre compreso fra -1 e size. tail viene calcolato come modulo
     * sulla dimensione della coda del valore precedente di tail incrementato di
     * uno, questo valore è sempre compreso fra 0 e size
     * 
     * Correttezza: ?
     */
    public void enqueue(int x) {
        if (isFull())
            throw new FullException("Impossibile aggiungere elemento. Coda piena.");
        if (isEmpty())
            head = 0;
        elements[tail] = x;
        tail = (tail + 1) % elements.length;

        assert repOK();
    }

    /**
     * Effetti collaterali: this è modificato se la coda non è vuota
     * 
     * Post-condizioni: Rimuove e restituisce l'elemento in testa alla coda this, se
     * presente. e solleva un'eccezione di tipo FullException se la coda è piena
     * this = [x1, x2, ..., x_k], k < n, this = [x2, ..., x_k]
     * 
     * Preservazione RI: la rimozione di un elemento non può mai risultare in un
     * incremento del numero di elementi quindi la coda non conterrà più elementi
     * della sua dimensione massima
     * 
     * Correttezza: ?
     */
    public int dequeue() {
        if (isEmpty())
            throw new EmptyException("Impossibile estrarre elemento. Coda vuota.");
        int r = elements[head];
        head = (head + 1) % elements.length;
        if (head == tail) {
            head = -1;
            tail = 0;
        }

        assert repOK();

        return r;
    }

    /**
     * Post-condizioni: restituisce true se la coda this è piena.
     * 
     * Correttezza: la coda è piena quando head e tail coincidono
     */
    public boolean isFull() {
        return head == tail;
    }

    /**
     * Post-condizioni: restituisce true se la coda this è vuota.
     * 
     * Correttezza: se la coda è vuota allora head == -1 e di conseguenza tail = 0
     */
    public boolean isEmpty() {
        return head == -1;
    }

    /**
     * Post-condizioni: restituisce true se l'invariante di rappresentazione è
     * valida
     */
    private boolean repOK() {
        return size() <= elements.length && elements != null && head >= -1 && head < elements.length && tail >= 0
                && tail < elements.length && (head != -1 || tail == 0);
    }

    /** Post-condizioni: restituisce il numero di elementi contenuti in this */
    public int size() {
        if (isEmpty())
            return 0;
        if (isFull())
            return elements.length;
        return (tail - head + elements.length) % elements.length;
    }

    @Override
    public String toString() {
        assert repOK();

        String r = "IntQueue : [";
        if (!isEmpty()) {
            int i;
            for (i = 0; i < size() - 1; i++)
                r += elements[(head + i) % elements.length] + ", ";
            r += elements[(head + i) % elements.length];
        }
        return r + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntQueue))
            return false;
        IntQueue other = (IntQueue) obj;

        if (size() != other.size() || elements.length != other.elements.length)
            return false;
        for (int i = 0; i < size(); i++)
            if (elements[(head + i) % elements.length] != other.elements[(other.head + i) % elements.length])
                return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = Integer.hashCode(size());
        for (int i = 0; i < size(); i++)
            hash = 31 * hash + Integer.hashCode(elements[(head + i) % elements.length]);

        return hash;
    }
}