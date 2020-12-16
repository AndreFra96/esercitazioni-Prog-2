import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GenericSet<K> implements Set<K> {
    List<K> mySet;

    public GenericSet() {
        mySet = new LinkedList<K>();
    }

    @Override
    public Iterator<K> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        return mySet.size();
    }

    @Override
    public boolean isEmpty() {
        return mySet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mySet.contains(o);
    }

    @Override
    public K choose() {
        if (mySet.isEmpty())
            throw new EmptyException("Set is empty");
        return mySet.get(mySet.size() - 1);
    }

    @Override
    public boolean add(K e) {
        if (mySet.contains(e))
            return false;
        mySet.add(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (this.isEmpty())
            return false;
        if (!(o.getClass().equals(mySet.get(0).getClass())))
            return false;
        @SuppressWarnings("unchecked") //Class checked
        K obj = (K) o;
        return mySet.remove(obj);
    }

}