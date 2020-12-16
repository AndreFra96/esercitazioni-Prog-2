import java.util.Objects;

/**
 * Le istanze di questa classe rappresentano pianeti nello spazio. Una Pianeta è
 * un CorpoCeleste la quale posizione varia di secondo in secondo in funzione
 * della sua velocità, la velocità di un pianeta varia grazie alle forze di
 * attrazione verso altri pianeti, in particolare attraverso il meccanismo di
 * interazione fra un pianeta e un qualsiasi altro corpo celeste
 */
public class Pianeta extends CorpoCeleste {

    public Pianeta(String nome, Punto posizione, Punto speed) {
        super(nome, posizione, speed);
    }

    /**
     * Esegue l'interazione fra due corpi celesti, in particolare esegue un
     * interazione fra Pianeta e un qualsiasi CorpoCeleste, questo metodo invoca
     * setSpeed su this e sull'oggetto in input calcolando l'incremento o il
     * decremento di velocità in base alla posizione dei due CorpiCeleste
     * 
     * @param c il CorpoCeleste che interagisce con this
     * @throws NullPointerException se c è null
     */
    @Override
    public void interact(CorpoCeleste c) {
        Objects.requireNonNull(c);
        Punto thisCoord = getCoordinate();
        Punto p = c.getCoordinate();

        int xstep = thisCoord.getX() < p.getX() ? 1 : -1;
        int ystep = thisCoord.getY() < p.getY() ? 1 : -1;
        int zstep = thisCoord.getZ() < p.getZ() ? 1 : -1;

        Punto speed = getSpeed();
        Punto cSpeed = c.getSpeed();

        // Modifico velocità di c
        c.setSpeed(new Punto(cSpeed.getX() - xstep, cSpeed.getY() - ystep, cSpeed.getZ() - zstep));

        // Modifico velocità di this
        setSpeed(new Punto(speed.getX() + xstep, speed.getY() + ystep, speed.getZ() + zstep));
    }

    @Override
    public String toString() {
        Punto coord = getCoordinate();
        Punto speed = getSpeed();
        return "Pianeta, nome: " + this.nome + ", pos: (" + coord.getX() + ", " + coord.getY() + ", " + coord.getZ()
                + "), vel: (" + speed.getX() + ", " + speed.getY() + ", " + speed.getZ() + ")";
    }

}