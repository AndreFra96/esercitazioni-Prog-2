import java.util.Iterator;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano insiemi (non limitati) di
 * interi. Gli oggetti di questo tipo sono mutabili. Un tipico IntSet è :
 * {x1,x2,x3,...,xn}, gli oggetti di questa classe permettono di accedere
 * facilmente all'elemento più grande e quello più piccolo dell'insieme
 * 
 * Funzione di astrazione: AF(mySet) = {mySet[i].intValue | 0 <= i < mySet.size}
 * 
 * Invariante di rappresentazione: mySet != null. Per ogni n compreso fra 0 e
 * mySet.size mySet[n] è un numero intero. In mySet non esistono duplicati. min
 * e max sono validi solo se il set ha dimensione maggiore di zero
 * 
 * Invariante di astrazione: l'IntSet più piccolo è l'IntSet vuoto che ha
 * dimensione zero, in un IntSet non sono presenti elementi duplicati
 */
public class MaxMinIntSet extends IntSet {
    protected int max = Integer.MIN_VALUE;
    protected int min = Integer.MAX_VALUE;

    @Override
    public void insert(int x) {
        if (x > max)
            max = x;
        if (x < min)
            min = x;
        super.insert(x);
    }

    @Override
    public void remove(int x) {
        super.remove(x);
        if (x == max && size() > 0) {
            Iterator<Integer> it = iterator();
            max = it.next();
            while (it.hasNext()) {
                int next = it.next();
                if (next > max) {
                    max = next;
                }
            }
        }
        if (x == min && size() > 0) {
            Iterator<Integer> it = iterator();
            min = it.next();
            while (it.hasNext()) {
                int next = it.next();
                if (next < min) {
                    min = next;
                }
            }
        }
    }

    int max() {
        return max;
    }

    int min() {
        return min;
    }
}