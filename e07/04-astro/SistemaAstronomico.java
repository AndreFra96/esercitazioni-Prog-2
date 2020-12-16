import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * OVERVIEW: Le istanze di questa classe rappresentano delle collezioni di corpi
 * celesti
 * 
 */
public class SistemaAstronomico implements Iterable<CorpoCeleste> {
    private List<CorpoCeleste> corpiCelesti;
    final String name;

    public SistemaAstronomico(String name, List<CorpoCeleste> corpiCelesti) {
        Objects.requireNonNull(corpiCelesti);
        this.corpiCelesti = corpiCelesti;
        this.name = name;
    }

    public void updateStatus() {
        updateSpeed();
        move();
    }

    private void updateSpeed() {
        for (int i = 0; i < corpiCelesti.size() - 1; i++){
            for (int j = i + 1; j < corpiCelesti.size(); j++) {
                corpiCelesti.get(i).interact(corpiCelesti.get(j));
            }
        }
    }

    public long totalEnergy() {
        long totalEnergy = 0;
        for (CorpoCeleste actual : corpiCelesti)
            totalEnergy += actual.getEnergia();
        return totalEnergy;
    }

    private void move() {
        for (CorpoCeleste actual : corpiCelesti) {
            actual.simulate();
        }
    }

    public Iterator<CorpoCeleste> nameSortedIterator() {

        Collections.sort(corpiCelesti, new Comparator<CorpoCeleste>() {
            @Override
            public int compare(CorpoCeleste o1, CorpoCeleste o2) {
                return o1.nome.compareTo(o2.nome);
            }
        });
        return corpiCelesti.iterator();

    }

    @Override
    public Iterator<CorpoCeleste> iterator() {
        return nameSortedIterator();
    }

}