import java.util.ArrayList;
import java.util.List;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano insiemi (non limitati) di
 * interi. Gli oggetti di questo tipo sono mutabili. Un tipico IntSet è :
 * {x1,x2,x3,...,xn}
 * 
 * Funzione di astrazione: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size}
 * 
 * Invariante di rappresentazione: mySet != null. Per ogni n compreso fra 0 e
 * mySet.size mySet[n] è un numero intero. In mySet non esistono duplicati
 * 
 * Invariante di astrazione: l'IntSet più piccolo è l'IntSet vuoto che ha
 * dimensione zero, in un IntSet non sono presenti elementi duplicati
 */
public class IntSet {
    /**
     * Struttura dati contenente gli elementi dell'insieme
     */
    private List<Integer> mySet;

    /**
     * Post-condizioni: Inizializza un nuovo insieme di interi vuoto
     * 
     * Preservazione RI: mySet è inizializzato con il costruttore vuoto di
     * ArrayList, non può quindi essere null e inizialmente contiene 0 elementi
     * perciò nessun elemento può essere null ne duplicato
     * 
     * Correttezza: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size} = {} se
     * size è 0
     */
    public IntSet() {
        mySet = new ArrayList<>();
    }

    /**
     * Effetti-collaterali: potrebbe modificare this Post-condizioni: Aggiunge x
     * all'insieme
     * 
     * Preservazione RI: se x è già presente in this.mySet non modifica this,
     * altrimenti inserisce un nuovo int, che non può essere null, all'interno di
     * mySet
     * 
     * Correttezza: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size} =
     * {mySet[i].intValue | 0 <= i < mySet.size} + newValue è uguale a
     * {mySet[i].intValue | 0 <= i < mySet.size} dove mySet[i].intValue = newValue
     * se newValue non è presente, oppure IntSet rimane invariato
     */
    public void insert(int x) {
        if (!(mySet.contains(x)))
            mySet.add(x);
        assert repOK();
    }

    /**
     * Effetti-collaterali: potrebbe modificare this
     * 
     * Post-condizioni: Rimuove x dall'insieme
     * 
     * Preservazione RI: Preserva l'invariante di rappresentazione poichè rimuove
     * l'elemento x dal set solamente se esso è presente, alla fine del metodo
     * l'IntSet conterrà gli stessi elementi che erano contenuti inizialmente fatta
     * eccezzione per l'elemento x se è presente
     * 
     * Correttezza:AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size} =
     * {mySet[i].intValue | 0 <= i < mySet.size, !mySet[i] = x}
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
     * 
     * Correttezza: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size} se è
     * valida allora mySet[index] corrisponde al valore intero all'indice index
     */
    public int get(int index) {
        return mySet.get(index);
    }

    /**
     * Post-condizioni: Restituisce un intero scelto arbitrariamente tra gli
     * elementi di una istanza, e sollevare un'eccezione di tipo EmptyException se
     * l'insieme è vuoto
     * 
     * Correttezza: Se non sono presenti elementi solleva un eccezione, altrimenti
     * restituisce sempre l'ultimo elemento dell'insieme
     */
    public int choose() {
        if (size() == 0)
            throw new EmptyException("Impossibile estrarre elemento da set vuoto");
        return mySet.get(mySet.size() - 1);
    }

    /**
     * Post-condizioni: Restituisce la cardinalità dell'insieme this
     * 
     * Correttezza: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size} => la
     * dimensione dell'IntSet corrisponde alla dimensione di mySet
     */
    public int size() {
        return mySet.size();
    }

    /**
     * Post-condizioni: restituisce true se la x è in this, false altrimenti
     * 
     * Correttezza: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size} => IntSet
     * contiene x se esso è uno degli elementi di mySet
     */
    public boolean contains(int x) {
        return mySet.contains(x);
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
        IntSet o = (IntSet) obj;
        if (o.size() != size())
            return false;
        for (Integer actual : mySet)
            if (!o.contains(actual))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = Integer.hashCode(size());
        for (Integer actual : mySet) {
            hash = 31 * hash + Integer.hashCode(actual);
        }
        return hash;
    }

    private boolean repOK() {
        return false;
    }

}