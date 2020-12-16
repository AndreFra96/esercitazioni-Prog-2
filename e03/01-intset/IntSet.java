import java.util.ArrayList;
import java.util.List;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano insiemi (non limitati) di
 * interi. Gli oggetti di questo tipo sono mutabili
 */
public class IntSet {

    

    /**
     * Struttura dati contenente gli elementi dell'insieme (Uso una lista per
     * esercizio)
     */
    private List<Integer> mySet;

    /**
     * Post-condizioni: Inizializza un nuovo insieme di interi vuoto
     */
    public IntSet() {
        mySet = new ArrayList<>();
    }

    /**
     * Effetti-collaterali: potrebbe modificare this: this_post = this + {x}
     * Post-condizioni: Aggiunge x all'insieme
     */
    public void insert(int x) {
        if (!(mySet.contains(x)))
            mySet.add(x);
    }

    /**
     * Effetti-collaterali: potrebbe modificare this: this_post = this - {x}
     * Post-condizioni: Rimuove x dall'insieme
     */
    public void remove(int x) {
        //Implementazione standard
        // mySet.remove(Integer.valueOf(x)); 

        // Implementazione ottimizzata
        int index = mySet.indexOf(x);
        if (index != -1) {
            int lastIndex = mySet.size() - 1;
            mySet.set(index, mySet.get(lastIndex));
            mySet.remove(lastIndex);
        }
    }

    /**
     * Post-condizioni: Restituisce un intero scelto arbitrariamente tra gli
     * elementi di una istanza, e sollevare un'eccezione di tipo EmptyException se
     * l'insieme è vuoto
     */
    public int choose() {
        if(size() == 0) throw new EmptyException("Impossibile estrarre elemento da set vuoto");
        //Prendo l'ultimo per le scelte implementative del remove
        return mySet.get(mySet.size() - 1); 
    }

    /**
     * Post-condizioni: Restituisce la cardinalità dell'insieme this
     */
    public int size() {
        return mySet.size();
    }

    /**
     * Post-condizioni: restituisce true se la x è in this, false altrimenti
     */
    public boolean contains(int x) {
        return mySet.contains(x);
    }

    /**
     * Post-condizioni: restituisce una rappresentazione testuale di this
     */
    @Override
    public String toString() {
        return mySet.toString();
    }

}