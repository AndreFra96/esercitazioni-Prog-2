
import java.util.ArrayList;
import java.util.List;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano insiemi (non limitati) di
 * interi. Gli oggetti di questo tipo sono mutabili. Un tipico IntSet è :
 * {x1,x2,x3,...,xn}
 * 
 * <p>
 * Funzione di astrazione: AF(mySet) = {mySet[i].intValue | 0 <= i <=
 * mySet.size}
 * 
 * <p>
 * Invariante di rappresentazione: mySet != null. Per ogni n compreso fra 0 e
 * mySet.size mySet[n] è un numero intero. In mySet non esistono duplicati
 */
public class IntSet {

    /**
     * Struttura dati contenente gli elementi dell'insieme
     */
    private List<Integer> mySet;

    /**
     * Post-condizioni: Inizializza un nuovo insieme di interi vuoto
     */
    public IntSet() {
        mySet = new ArrayList<>();
    }

    /**
     * Effetti-collaterali: potrebbe modificare this Post-condizioni: Aggiunge x
     * all'insieme
     */
    public void insert(int x) {
        if (!(mySet.contains(x)))
            mySet.add(x);
        assert repOK();
    }

    /**
     * Effetti-collaterali: potrebbe modificare this Post-condizioni: Rimuove x
     * dall'insieme
     */
    public void remove(int x) {
        int index = mySet.indexOf(x);
        if (index != -1) {
            int lastIndex = mySet.size() - 1;
            mySet.set(index, mySet.get(lastIndex));
            mySet.remove(lastIndex);
        }
        assert repOK();
    }

    /**
     * Post-condizioni: Restituisce l'elemento in posizione x solleva
     * IndexOutOfBoundException se x è fuori dal range index < 0 || index >= size()
     */
    public int get(int index) {
        return mySet.get(index);
    }

    /**
     * Post-condizioni: Restituisce un intero scelto arbitrariamente tra gli
     * elementi di una istanza, e sollevare un'eccezione di tipo EmptyException se
     * l'insieme è vuoto
     */
    public int choose() {
        if (size() == 0)
            throw new EmptyException("Impossibile estrarre elemento da set vuoto");
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
     * Effetti-collaterali: potrebbe modificare this Post-condizioni: riposiziona
     * gli elementi di mySet ordinandoli in ordine crescente
     */
    public void sort() {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (mySet.get(i) < mySet.get(j)) {
                    int temp = mySet.get(i);
                    mySet.set(i, mySet.get(j));
                    mySet.set(j, temp);
                }
            }
        }
        assert repOK();
    }

    /**
     * Post-condizioni: restituisce una rappresentazione testuale di this
     */
    @Override
    public String toString() {
        String set = "IntSet: {";
        int i;
        for (i = 0; i < size() - 1; i++)
            set += mySet.get(i) + ", ";

        if (size() > 0)
            set += mySet.get(i);

        set += "}";
        return set;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntSet))
            return false;

        IntSet set = (IntSet) obj;

        if (set.size() != size())
            return false;
        int i;
        for (i = 0; i < size(); i++)
            if (!(this.contains(set.get(i))))
                break;
        if (i != size()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        sort();
        String repString = "";
        for(Integer actual : mySet){
            repString+= actual+"-";
        }
        return repString.hashCode();
    }

    /**
     * Effetti-collaterali: potrebbe modificare l'ordine degli elementi in this
     * Post-condizioni: restituisce true se l'invariante di rappresentazione è rispettato, false altrimenti
     */
    private boolean repOK() {
        if (this.mySet == null)
            return false;
        sort();
        for (int i = 1; i < size(); i++)
            if (mySet.get(i - 1) == mySet.get(i))
                return false;

        return true;
    }

}