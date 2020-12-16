import java.util.Iterator;
import java.util.LinkedList;
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
public class IntSet implements Iterable<Integer> {

    /**
     * Struttura dati contenente gli elementi dell'insieme
     */
    protected List<Integer> set;

    public IntSet() {
        set = new LinkedList<>();
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
        if (!(contains(x)))
            set.add(x);
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
        if (contains(x))
            set.remove(Integer.valueOf(x));
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
        if (set.size() <= 0)
            throw new EmptyException();
        return set.get(0);
    }

    /**
     * Post-condizioni: Restituisce la cardinalità dell'insieme this
     * 
     * Correttezza: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size} => la
     * dimensione dell'IntSet corrisponde alla dimensione di mySet
     */
    public int size() {
        return set.size();
    }

    /**
     * Post-condizioni: restituisce true se la x è in this, false altrimenti
     * 
     * Correttezza: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size} => IntSet
     * contiene x se esso è uno degli elementi di mySet
     */
    public boolean contains(int x) {
        return set.contains(x);
    }

    @Override
    public Iterator<Integer> iterator() {

        return new Iterator<Integer>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return set.size() > index;
            }

            @Override
            public Integer next() {
                return set.get(index++);
            }

        };
    }

}