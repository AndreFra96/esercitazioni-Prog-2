import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano delle liste ordinate di
 * numeri interi positivi e negativi, zero escluso
 * 
 * Funzione di astrazione: AF(value,right,left) = {} se empty restituisce true,
 * {AF(left),value} se left è diverso da null e right è null (rappresenta
 * l'ultimo elemento della lista), {value,AF(right)} se right è diverso da null
 * e left è null (rappresenta il primo elemento della lista),
 * {AF(left),value,AF(right)} se right e left sono diversi da null (rappresenta
 * un elemento centrale della lista)
 * 
 * Invariante di rappresentazione: se right e left sono null allora value è zero
 * e this è la lista vuota
 * 
 * Invariante di astrazione: la lista vuota è la lista con valore 0 e right e
 * left null, se right è diversa da null e left è null allora this è l'elemento
 * più piccolo della lista, se right è null e left è diversa da null allora: se
 * value è 0 right è l'elemento più grande della lista, altrimenti è this
 * l'elemento più grande
 */
public class OrderedIntList implements Iterable<Integer>{
    private int value;
    private OrderedIntList right;
    private OrderedIntList left;

    /**
     * Costruisce una nuova lista vuota
     * 
     * Preservazione RI: senza inizializzazione right e left sono null e value è 0
     * 
     * Correttezza: AF(value,right,left) = AF(0,null,null) = {}
     */
    public OrderedIntList() {
        assert repOk();
    }

    /**
     * Costruisce una nuova lista tramite il builder in input
     * 
     * Preservazione RI: se rightEl e leftEl sono null allora imposto value a 0
     * 
     * Correttezza: ?
     * 
     * @param builder builder
     */
    private OrderedIntList(Builder builder) {
        value = builder.initVal;
        right = builder.rightEl;
        left = builder.leftEl;
        if (builder.rightEl == null && builder.leftEl == null) {
            value = 0;
        }
        assert repOk();
    }

    /**
     * OVERVIEW: Le istanze di questa classe rappresentano builder specifici per
     * oggetti del tipo OrderedIntList, Builder permette di costruire OrderedIntList
     * con qualsiasi combinazione di valori, eventuali controlli sulla correttezza
     * della rappresentazione sono delegati al costruttore che utilizza Builder
     * richiamato attraverso il metodo build
     */
    private static class Builder {
        private int initVal;
        private OrderedIntList rightEl;
        private OrderedIntList leftEl;

        public Builder(int n) {
            this.initVal = n;
        }

        public Builder right(OrderedIntList lst) {
            this.rightEl = lst;
            return this;
        }

        public Builder left(OrderedIntList lst) {
            this.leftEl = lst;
            return this;
        }

        public OrderedIntList build() {
            return new OrderedIntList(this);
        }
    }

    /**
     * Controlla se this è una lista vuota
     * 
     * Correttezza: AF(value,right,left) = AF(0,null,null) = {}
     * 
     * @return true se this è una lista vuota, false altrimenti
     */
    public boolean empty() {
        return right == null && left == null && value == 0;
    }

    /**
     * Aggiunge un nuovo elemento alla lista, se esso non è già presente
     * 
     * Preservazione RI: se this è valido allora l'inserimento di un nuovo intero
     * nella lista comporta la presenza di almeno un elemento, dato che add non
     * restituisce mai una lista vuota possiamo dedurre che add mantiene valido
     * l'invariante di rappresentazione
     * 
     * @param n il nuovo valore da inserire
     * @throws DuplicateException se n è gia presente nella lista
     */
    public void add(int n) {
        if (n == value) {
            throw new DuplicateException();
        }

        // Inserimento in lista vuota
        if (empty()) {
            if (n > 0) {
                right = new Builder(n).left(this).build();
            } else {
                left = new Builder(n).right(this).build();
            }
            // Inserimento agli estremi
        } else if (n > value && right == null) {
            right = new Builder(n).left(left.right).build();
        } else if (n < value && left == null) {
            left = new Builder(n).right(right.left).build();
            // Inserimento al centro
        } else if (n > value && right != null) {
            if (n < right.value) {
                OrderedIntList lst;
                right.left = lst = new Builder(n).right(right).left(this).build();
                right = lst;
            } else {
                right.add(n);
            }
        } else if (n < value && left != null) {
            if (n > left.value) {
                OrderedIntList lst;
                left.right = lst = new Builder(n).right(this).left(left).build();
                left = lst;
            } else {
                left.add(n);
            }
        }

        assert repOk();
    }

    /**
     * Rimuove un elemento dalla lista, se esso è presente
     * 
     * @param n il valore da rimuovere dalla lista
     */
    public void remove(int n) {
        if (!isIn(n)) {
            return;
        }
        if (value == n) {
            if (left != null && right != null) {
                left.right = right;
                right.left = left;
            } else if (left == null) {
                right.left = null;
            } else if (right == null) {
                left.right = null;
            }
        } else if (value > n) {
            left.remove(n);
        } else {
            right.remove(n);
        }
    }

    /**
     * Controlla se n è uno degli interi della lista
     * 
     * @param n il numero da cercare nella lista
     * @return true se n è un elemento della lista, false altrimenti
     */
    public boolean isIn(int n) {

        if (n == 0) {
            return false;
        }
        if (value == n) {
            return true;
        }
        if (n > value) {
            if (right == null) {
                return false;
            }
            return right.isIn(n);
        }
        if (left == null) {
            return false;
        }
        return left.isIn(n);
    }

    /**
     * Restituisce l'OrderedIntList avente come valore l'elemento più piccolo della
     * lista, escluso lo zero
     * 
     * Correttezza: il primo elemento della lista è this se left == null, oppure
     * right se left == null e this.value = 0
     * 
     * @return OrderedIntList il quale valore è il più piccolo della lista
     */
    private OrderedIntList firstListEl() {
        if (value == 0 && left == null) {
            return right;
        }
        if (left == null) {
            return this;
        }
        return left.firstListEl();
    }

    /**
     * Restituisce l'OrderedIntList avente come valore l'elemento più grande della
     * lista
     * 
     * Correttezza: l'ultimo elemento della lista è this se right == null
     * 
     * @return OrderedIntList il quale valore è il più piccolo della lista
     */
    private OrderedIntList lastListEl() {
        if (right == null) {
            return this;
        }
        return right.lastListEl();
    }

    /**
     * Iteratore sugli elementi di this ordinati in ordine crescente
     * 
     * @return Iterator<Integer> sugli elementi di this
     */
    public Iterator<Integer> smallToBig() {
        return new Iterator<Integer>() {
            OrderedIntList cache = firstListEl();

            @Override
            public boolean hasNext() {
                return this.cache != null;
            }

            @Override
            public Integer next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                Integer n = this.cache.value;
                this.cache = this.cache.right;

                // Nascondo lo zero poichè non è un elemento della lista
                if (this.cache != null && this.cache.value == 0 && this.cache.right != null)
                    this.cache = this.cache.right;

                return n;
            }
        };
    }

    /**
     * Iteratore sugli elementi di this ordinati in ordine decrescente
     * 
     * @return Iterator<Integer> sugli elementi di this
     */
    public Iterator<Integer> bigToSmall() {
        return new Iterator<Integer>() {
            OrderedIntList cache = lastListEl();

            @Override
            public boolean hasNext() {
                // Se l'ultimo elemento è 0 restituisco false e non lo mostro
                if (this.cache != null && this.cache.value == 0 && this.cache.left == null) {
                    return false;
                }
                return this.cache != null;
            }

            @Override
            public Integer next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                Integer n = this.cache.value;
                this.cache = this.cache.left;

                // Nascondo lo zero poichè non è un elemento della lista
                if (this.cache != null && this.cache.value == 0 && this.cache.left != null) {
                    this.cache = this.cache.left;
                }
                return n;
            }
        };
    }

    
    public String toString() { // TODO: modificare, questo è utile per stampare i singoli blocchi
        
        if (left == null && right == null) {
            return "" + value;
        }
        if (left == null) {
            return value + "," + right.value;
        }
        if (right == null) {
            return left.value + "," + value;
        }
        return left.value + "," + value + "," + right.value;
    }

    @Override
    public boolean equals(Object obj) { // TODO ask: Posso utilizzare iteratore nell'equals?
        if (!(obj instanceof OrderedIntList))
            return false;
        OrderedIntList match = (OrderedIntList) obj;

        Iterator<Integer> stb = smallToBig();
        Iterator<Integer> mstb = match.smallToBig();

        while (stb.hasNext() || mstb.hasNext()) {
            if (!(stb.hasNext()) || !(mstb.hasNext()) || (mstb.next() != stb.next()))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() { // TODO ask: Posso utilizzare iteratore nell'hashCode?
        int hash = 1;
        Iterator<Integer> stb = smallToBig();
        while (stb.hasNext()) {
            hash = 31 + hash * Integer.hashCode(stb.next());
        }
        return hash;
    }

    private boolean repOk() {
        if ((right == null && left == null) && value != 0)
            return false;
        return true;
    }

    @Override
    public Iterator<Integer> iterator() {
        return smallToBig();
    }

}