import java.util.HashSet;
import java.util.Set;

/**
 * OVERVIEW: le istanze di questa classe rappresentano una mappa, ossia una
 * struttura dati che permette di associare, a ciascuna k (chiave) di tipo
 * stringa, un v (valore) di tipo intero. Una mappa tipica è {k_0 -> v_0, k_2 ->
 * v_2, ... , k_n -> v_n | n = size - 1}
 * 
 * Funzione di astrazione: AF(elements) = { elements.get(0).key ->
 * elements.get(0).value elements.get(1).key -> elments.get(1).value ...
 * elements.get(elements.size -1).key -> elements.get(elements.size -1).value }
 * 
 * Invariante di rappresentazione: elements è diverso da null, ogni elemento di
 * elements è diverso da null, tutti i nodi di elements hanno una chiave diversa
 * 
 * Invariante di astrazione: le chiavi della mappa sono univoche, ad ogni chiave
 * è associato un valore diverso da null
 */
public class SimpleMap {

    /**
     * Struttura dati contenente gli elementi della mappa.
     * 
     * Usando una struttura dati di tipo Set e sapendo che il tipo Nodo considera
     * uguali due oggetti con la stessa chiave, sono sicuro di non avere chiavi
     * ripetute nella mappa
     */
    private Set<Nodo<String, Integer>> elements;

    /**
     * Post-condizioni: inizializza e restituisce una nuova Mappa vuota
     * 
     * Preservazione RI: Preserva l'invariante di rappresentazione poichè
     * restituisce sempre la mappa vuota = {}
     * 
     * Correttezza: this è un set vuoto che rappresenta la mappa vuota, un oggetto
     * valido secondo la funzione di astrazione
     */
    public SimpleMap() {
        this.elements = new HashSet<>();
        assert repOK();
    }

    /**
     * Effetti-collaterali: potrebbe modificare this
     * 
     * Post-condizioni: associa alla chiave key della mappa il valore value, se la
     * mappa contiene già key il vecchio valore viene rimpiazzato, solleva
     * NullPointerException se key è null
     * 
     * Preservazione RI: Preserva l'invariante di rappresentazione poichè il
     * costruttore di Nodo non resituisce mai un nodo null, e operazioni contains e
     * remove di un set non possono introdurre elementi nulli, inoltre la struttura
     * dati scelta per l'implementazione non ammette duplicati quindi non possono
     * essere presenti chiavi ripetute
     * 
     * Correttezza: il metodo put inizialmente utilizza il costruttore di Nodo per
     * costruire un nuovo nodo con i parametri in input, il costruttore di Nodo può
     * costruire solamente nodi diversi da null e con chiave diversa da null
     * altrimenti solleva una NullPointerException, dato che l'integer contenuto nel
     * nodo è prodotto grazie al boxing di un int non può essere null. Dopo di che
     * il metodo contains verifica la presenza del nodo fra gli elementi di this e
     * se il nodo è già presente il metodo remove rimuove il nodo presente, dopo di
     * che è possibile inserire il nuovo nodo avendo la certezza di sostituirlo al
     * precedente se esso era già presente o semplicemente inserirne uno nuovo
     * ottenendo sempre una mappa valida
     * 
     */
    public void put(String key, int value) {
        Nodo<String, Integer> newNode = new Nodo<>(key, value);
        if (elements.contains(newNode)) {
            elements.remove(newNode);
        }
        elements.add(newNode);
        assert repOK();

    }

    /**
     * Effetti-collaterali: potrebbe modifcare this
     * 
     * Post-condizioni: rimuove il Nodo corrispondente alla chiave key dalla mappa
     * se è presente, restituisce il valore di quel nodo o null se non esiste un
     * Nodo con la chiave key, solleva NullPointerException se key è null
     * 
     * Preservazione RI: Preserva l'invariante di rappresentazione poichè
     * l'operazione remove, che è l'unica che potrebbe modificare elements, per la
     * sua implementazione non può mai rendere il set, ne alcun elmento di esso,
     * null
     * 
     * Correttezza: se esiste un nodo fra gli elementi la cui chiave è uguale a key
     * esso viene rimosso, altrimenti this non viene modificato. Una mappa valida
     * dalla quale viene rimosso un elemento rappresenta sempre una mappa valida
     */
    public void remove(String key) {

        Nodo<String, Integer> delete = null; // Inserito per evitare ConcurrentModificationException
        for (Nodo<String, Integer> actual : elements)
            if (actual.getKey().equals(key))
                delete = actual;

        if (delete != null)
            elements.remove(delete);

        assert repOK();

    }

    /**
     * Post-condizioni: restituisce un intero corrispondente al numero di nodi
     * presenti nella mappa
     * 
     * Correttezza: il numero di elementi presenti nella mappa è uguale alla
     * cardinalità di elements
     */
    public int size() {
        return elements.size();
    }

    /**
     * Post-condizioni: restituisce true se la mappa contiene un nodo con chiave
     * key, false altrimenti
     * 
     * Correttezza: la mappa contiene la chiave key se esiste almeno un elemento la
     * cui chiave è uguale a key, altrimenti non è contenuta
     */
    public boolean containsKey(String key) {
        for (Nodo<String, Integer> actual : elements)
            if (actual.getKey().equals(key))
                return true;
        return false;
    }

    /**
     * Post-condizioni: restituisce il valore del nodo con chiave key, se non esiste
     * restituisce null, solleva NullPointerException se key è null
     * 
     * Correttezza: se uno degli elementi di elements ha chiave uguale a key allora
     * è l'elemento cercato e la funzione restituisce il valore di quel nodo, se
     * nessuna chiave fra i gli elementi di this è uguale a key allora la chiave non
     * è presente nella mappa e viene sollevata un'eccezione
     */
    public int get(String key) {
        for (Nodo<String, Integer> actual : elements)
            if (actual.getKey().equals(key))
                return actual.getValue();
        // TODO: verificare se è possibile utilizzare eccezione in questo modo
        throw new IllegalArgumentException("La chiave" + key + "non è presente nella mappa");
    }

    @Override
    public String toString() {
        String returnString = "Mappa: \n";
        for (Nodo<String, Integer> n : elements)
            returnString += n.toString() + "\n";

        return returnString;
    }

    /**
     * Implementa l'invariante di rappresentazione 
     * 
     * Post-condizioni: restituisce true
     * se this rispetta l'invariante di rappresentazione, false altrimenti
     */
    private boolean repOK() {
        if (elements == null)
            return false;
        for (Nodo<String, Integer> actual : elements) {
            if (actual == null)
                return false;
        }
        // Sono sicuro di non avere duplicati poichè la struttura dati scelta non può
        // contenerne
        return true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SimpleMap))
            return false;
        SimpleMap o = (SimpleMap) obj;
        if (size() != o.size())
            return false;

        for (Nodo<String, Integer> n : elements) {
            if (!o.containsKey(n.getKey()) || o.get(n.getKey()) != n.getValue())
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = size();
        for (Nodo<String, Integer> actual : elements)
            hash = 31 * hash + actual.hashCode();

        return hash;
    }

}