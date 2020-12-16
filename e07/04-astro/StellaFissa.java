import java.util.Objects;

/**
 * Le istanze di questa classe rappresentano stelle fisse nello spazio. Una
 * StellaFissa è un CorpoCeleste la quale posizione non può mai essere
 * modificata in funzione della velocità poichè la velocità è sempre zero
 */
public class StellaFissa extends CorpoCeleste {

    public StellaFissa(String nome, Punto posizione) {
        super(nome, posizione, new Punto(0, 0, 0));
    }

    /**
     * Sovrascrive il metodo setSpeed di CorpoCeleste, impostando sempre la velocità
     * a zero, dato che la StellaFissa è un particolare CorpoCeleste la quale
     * velocità non viene mai modificata, rimane fissa a zero.
     */
    @Override
    protected void setSpeed(Punto speed) {
        super.setSpeed(new Punto(0, 0, 0));
    }

    /**
     * Esegue l'interazione fra due corpi celesti, in particolare esegue un
     * interazione fra StellaFissa e un qualsiasi CorpoCeleste, essendo la
     * StellaFissa un corpo con velocità costante zero la velocità di this non viene
     * modificata, mentre viene invocato il metodo setSpeed sul CorpoCeleste in
     * input, ciò può comportare la modifica della velocità di quest'ultimo
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

        Punto speed = c.getSpeed();

        // Modifico velocità di c
        c.setSpeed(new Punto(speed.getX() - xstep, speed.getY() - ystep, speed.getZ() - zstep));
    }

    @Override
    public String toString() {
        Punto coord = getCoordinate();
        return "Stella fissa, nome: " + this.nome + ", pos: (" + coord.getX() + ", " + coord.getY() + ", "
                + coord.getZ() + ")";
    }

}