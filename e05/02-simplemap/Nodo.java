import java.util.Objects;

/**
 * OVERVIEW: un instanza di Nodo è un oggetto che associa ad una variabile di
 * tipo K una variabile di tipo V, un Nodo è mutabile, la chiave di un Nodo è
 * immutabile. Accetta qualsiasi tipo di dato come chiave e come valore escluso
 * null.
 * 
 * Due nodi si dicono uguali se la loro chiave è uguale poichè l'astrazione
 * corrente prevede nodi univoci all'interno di un sistema. La presenza di due
 * nodi con la stessa chiave non è prevista dall'astrazione e può generare
 * problemi.
 * 
 * Le strutture dati preferibili per il contenimento di nodi sono quelle in cui
 * non è prevista la presenza di elementi duplicati (es: Set)
 * 
 * <p>
 * Funzione di astrazione: AF(key,value) = key -> value
 * 
 * <p>
 * Invariante di rappresentazione: key deve essere diverso da null, value deve
 * essere diverso da null
 * 
 * <p>
 * Invariante di astrazione: non sono ammessi nodi con chiave o valore nulli
 */
public class Nodo<K, V> {

    /** Struttura dati contenente la chiave del nodo */
    private final K key;
    /** Struttura dati contenente il valore del nodo */
    private V value;

    /**
     * Post-condizioni: inizializza e restituisce un nuovo Nodo<K,V> solleva
     * NullPointerException se key e/o value sono null
     * 
     * Preservazione RI: Preserva l'invariante di rappresentazione poichè nel caso
     * il cui key o value siano null solleva un'eccezione
     * 
     * Correttezza: ogni coppia di valori non null in input costruisce un nodo
     * valido secondo l'invariante di rappresentazione, in caso di input null viene
     * sollevata un'eccezione
     */
    public Nodo(K key, V value) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
        assert repOK();
    }

    /**
     * Post-condizioni: restituisce una variabile di tipo K contente la chiave del
     * nodo
     */
    public K getKey() {
        return key;
    }

    /**
     * Post-condizioni: restituisce una variabile di tipo V contente il valore del
     * nodo
     */
    public V getValue() {
        return value;
    }

    /**
     * Effetti-collaterali: potrebbe modificare this
     * 
     * Post-condizioni: sostituisce al valore del nodo attuale il valore in input,
     * solleva NullPointerException se il valore in input è null
     * 
     * Preservazione RI: Preserva l'invariante di rappresentazione poichè la chiave
     * non viene modificata mentre prima di modificare il valore controllo che
     * quello in input sia diverso da null
     * 
     * Correttezza: se this è un nodo valido qualsiasi valore diverso da null può
     * essere sostituito al valore ottenendo sempre un nodo valido, se l'input è
     * null viene sollevata un'eccezione
     */
    public void setValue(V value) {
        this.value = Objects.requireNonNull(value);
        assert repOK();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Nodo<?, ?>))
            return false;
        Nodo<?, ?> temp = (Nodo<?, ?>) obj;
        if (!temp.getKey().getClass().equals(getKey().getClass())) {
            return false;
        }
        return temp.getKey().equals(getKey());
    }

    /**
     * Implementa l'invariante di rappresentazione
     * <p>
     * Post-condizioni: restituisce true se l'invariante di rappresentazione è
     * rispettato, false altrimenti
     */
    private boolean repOK() {
        return getKey() != null && getValue() != null;
    }

    /**
     * Post-condizioni: hashCode solo sulla chiave per mantenere valido RI
     */
    @Override
    public int hashCode() {
        return 31 * getKey().hashCode();
    }

    @Override
    public String toString() {
        return getKey() + "->" + getValue();
    }

}