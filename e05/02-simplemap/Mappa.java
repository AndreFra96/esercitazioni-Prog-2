import java.util.HashSet;
import java.util.Set;

/**
 * OVERVIEW: le istanze di questa classe rappresentano una mappa, ossia una
 * struttura dati che permette di associare, a ciascuna k (chiave), un v
 * (valore). Due mappe sono uguali se hanno tutti i nodi uguali e se ad ogni
 * nodo uguale corrisponde un valore uguale.
 * 
 * Funzione di astrazione: AF(elements) = elements.get(0).key ->
 * elements.get(0).value elements.get(1).key -> elments.get(1).value ...
 * elements.get(elements.size -1).key -> elements.get(elements.size -1).value
 * 
 * Invariante di rappresentazione: elements è diverso da null ogni elemento di
 * elements è diverso da null tutti i nodi di elements hanno una chiave diversa
 */
public class Mappa<K, V> {

    /**
     * Struttura dati contenente gli elementi della mappa.
     * <p>
     * Usando una struttura dati di tipo Set e sapendo che il tipo Nodo considera
     * uguali due oggetti con la stessa chiave, sono sicuro di non avere chiavi
     * ripetute nella mappa
     */
    private Set<Nodo<K, V>> elements;

    /**
     * Post-condizioni: inizializza e restituisce una nuova Mappa vuota
     * 
     * Preserva l'invariante di rappresentazione poichè restituisce sempre la mappa
     * vuota = {}
     */
    public Mappa() {
        this.elements = new HashSet<>();
        assert repOK();
    }

    /**
     * Post-condizioni: inizializza e restituisce la Mappa copia della Mappa in
     * input
     * 
     * Preserva l'invariante di rappresentazione poichè se old è una mappa valida
     * allora this essendo una copia di tutti gli elementi di old non può che essere
     * una mappa valida
     */
    public Mappa(Mappa<K, V> old) {
        this.elements = new HashSet<>(old.elements);
        assert repOK();

    }

    /**
     * Effetti-collaterali: potrebbe modificare this
     * <p>
     * Post-condizioni: associa alla chiave key della mappa il valore value, se la
     * mappa contiene già key il vecchio valore viene rimpiazzato, solleva
     * NullPointerException se key è null
     * 
     * Preserva l'invariante di rappresentazione poichè il costruttore di Nodo non
     * resituisce mai un nodo null, e operazioni contains e remove di un set non
     * possono introdurre elementi nulli e inoltre la struttura dati scelta per
     * l'implementazione non ammette duplicati
     */
    public void put(K key, V value) {
        Nodo<K, V> newNode = new Nodo<>(key, value);
        if (elements.contains(newNode)) {
            elements.remove(newNode);
        }
        elements.add(newNode);
        assert repOK();

    }

    /**
     * Effetti-collaterali: potrebbe modifcare this
     * <p>
     * Post-condizioni: rimuove il Nodo corrispondente alla chiave key dalla mappa
     * se è presente, restituisce il valore di quel nodo o null se non esiste un
     * Nodo con la chiave key, solleva NullPointerException se key è null
     * 
     * Preserva l'invariante di rappresentazione poichè l'operazione remove, che è
     * l'unica che potrebbe modificare elements, per la sua implementazione non può
     * mai rendere il set, ne alcun elmento di esso, null
     */
    public V remove(K key) {
        assert repOK();

        // Implementazione con getKey
        for (Nodo<K, V> actual : elements) {
            if (actual.getKey().equals(key)) {
                elements.remove(actual);
                return actual.getValue();
            }
        }

        // Implementazione con hashCode (l'hashcode di un nodo corrisponde all'hashcode
        // della sua chiave)
        // for(Nodo<K,V> actual : elements){
        // if(actual.hashCode() == key.hashCode()){
        // elements.remove(actual);
        // return actual.getValue();
        // }
        // }

        return null;
    }

    /**
     * Post-condizioni: restituisce un intero corrispondente al numero di nodi
     * presenti nella mappa
     */
    public int size() {
        return elements.size();
    }

    /**
     * Post-condizioni: restituisce il valore del nodo con chiave key, se non esiste
     * restituisce null, solleva NullPointerException se key è null
     */
    public V get(K key) {
        // Implementazione con getKey
        for (Nodo<K, V> actual : elements) {
            if (actual.getKey().equals(key)) {
                return actual.getValue();
            }
        }
        // Implementazione con hashCode (l'hashcode di un nodo corrisponde all'hashcode
        // della sua chiave)
        // for(Nodo<K,V> actual : elements){
        // if(actual.hashCode() == key.hashCode()){
        // return actual.getValue();
        // }
        // }
        return null;
    }

    @Override
    public String toString() {
        String returnString = "Mappa: \n";
        for (Nodo<K, V> n : elements) {
            returnString += n.toString() + "\n";
        }
        return returnString;
    }

    /**
     * Implementa l'invariante di rappresentazione Post-condizioni: restituisce true
     * se this rispetta l'invariante di rappresentazione, false altrimenti
     */
    private boolean repOK() {
        if (elements == null)
            return false;
        for (Nodo<K, V> actual : elements) {
            if (actual == null)
                return false;
        }
        // Sono sicuro di non avere duplicati poichè la struttura dati scelta non può
        // contenerne
        return true;
    }

    public boolean equals(Object obj) {
        // Controllo che obj sia una mappa
        if (!(obj instanceof Mappa))
            return false;
        // Controllo che obj sia una mappa del tipo Mappa<K,V> (SBAGLIATO FARLO IN
        // QUESTO MODO, COME LO FACCIO?)
        Mappa<?, ?> match = (Mappa<?, ?>) obj;
        System.out.println(match.getClass());
        if (!(match.getClass().equals(getClass())))
            return false;

        @SuppressWarnings("unchecked") // Sono sicuro che il tipo sia Mappa<K,V>
        Mappa<K, V> matchSameClass = (Mappa<K, V>) match;
        // Controllo che this e obj abbiano lo stesso numero di elementi
        if (matchSameClass.size() != size())
            return false;
        // Controllo se ci sono elementi di this non presenti in obj
        for (Nodo<K, V> n : elements) {
            V compare = matchSameClass.get(n.getKey());
            if (compare == null)
                return false;
            if (!(compare.equals(n.getValue())))
                return false;
        }
        return true;
    }

}