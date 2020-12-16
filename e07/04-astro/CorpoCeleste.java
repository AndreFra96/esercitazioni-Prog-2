import java.util.Objects;

/**
 * Un CorpoCeleste è una classe astratta rappresentante oggetti nello spezio.
 * 
 * Ogni CorpoCeleste ha un punto dello spazio in cui è posizionato attualmente,
 * ha energia cinetica ed energia potenziale
 * 
 * Funzione di astrazione: AF(coordinate,energiaCinetica,energiaPotenziale,nome)
 * rappresenta il CorpoCeleste chiamato {@code nome} posizionato nel punto
 * {@code coordinate} dello spazio, il CorpoCeleste ha energia potenziale pari a
 * {@code energiaPotenziale} e energia cinetica parti a {@code energiaCinetica}
 * 
 * Invariante di rapresentazione: Punto != null, nome != null, energiaCinetica
 * >= 0, energiaPotenziale >= 0
 * 
 */
public abstract class CorpoCeleste {
    private Punto coordinate;
    private Punto speed;
    final String nome;

    /**
     * Esegue l'interazione fra due corpi celesti, l'interazione fra i due corpi
     * celesti può comportare la modifica della velocità di entrambi
     * 
     * @param c il CorpoCeleste che interagisce con this
     * @throws NullPointerException se c è null
     */
    public abstract void interact(CorpoCeleste c);

    /**
     * Esegue una simulazione di un secondo di vita del corpo celeste, modifica le
     * coordinate del corpo celeste in base alle coordinate di speed
     * 
     * coordinate' = (coordinate.getX() + speed.getX(), coordinate.getY() +
     * speed.getY(), coordinate.getZ() + speed.getZ())
     * 
     */
    public void simulate() {
        coordinate = new Punto(coordinate.getX() + speed.getX(), coordinate.getY() + speed.getY(),
                coordinate.getZ() + speed.getZ());
    }

    /**
     * Inizializza un nuovo CorpoCeleste
     * 
     * @param nome      nome del corpo celeste
     * @param posizione posizione nello spazio del corpo celeste
     * @param speed     velocità del corpo celeste
     * @throws NullPointerException se nome è null e/o posizione è null e/o speed è
     *                              null
     */
    public CorpoCeleste(String nome, Punto posizione, Punto speed) {
        this.nome = Objects.requireNonNull(nome);
        this.coordinate = Objects.requireNonNull(posizione);
        this.speed = Objects.requireNonNull(speed);
    }

    /**
     * Sposta il corpo celeste in posizione {@code coordinate}, modifica
     * this.coordinate
     * 
     * Permette alle classi che estendono CorpoCeleste di modificare le coordinate
     * del corpo celeste assicurando la preservazione dell'invariante di
     * rappresentazione
     * 
     * @param coordinate le nuove coordinate del corpo celeste
     */
    protected void setCoordinate(Punto coordinate) {
        this.coordinate = Objects.requireNonNull(coordinate);
    }

    /**
     * Modifica la velocità del corpo celeste
     * 
     * Permette alle classi che estendono CorpoCeleste di modificare la velocità del
     * corpo celeste assicurando la preservazione dell'invariante di
     * rappresentazione
     * 
     * @param coordinate la nuova velocità del corpo celeste
     */
    protected void setSpeed(Punto speed) {
        this.speed = Objects.requireNonNull(speed);
    }

    /**
     * Restituisce un nuovo punto con le stesse coordinate della posizione del corpo
     * celeste
     * 
     * @return punto con le coordinate di this
     */
    public Punto getCoordinate() {
        return new Punto(coordinate.getX(), coordinate.getY(), coordinate.getZ());
    }

    /**
     * Restituisce un nuovo punto con le stesse coordinate della velocità del corpo
     * celeste
     * 
     * @return punto con la velocità di this
     */
    public Punto getSpeed() {
        return new Punto(speed.getX(), speed.getY(), speed.getZ());
    }

    /**
     * Restituisce l'energia cinetica di this
     * 
     * @return energia cinetica
     */
    public int energiaCinetica() {
        return speed.norma();
    }

    /**
     * Restituisce l'energia potenziale di this
     * 
     * @return energia potenziale
     */
    public int energiaPotenziale() {
        return coordinate.norma();
    }

    /**
     * Restituisce l'energia di this, calcolata come prodotto fra l'energia cinetica
     * e quella potenziale
     * 
     * @return energia di this
     */
    public long getEnergia() {
        return energiaCinetica() * energiaPotenziale();
    }

    /**
     * Restituisce il nome del corpo celeste
     * 
     * @return nome del corpo celeste
     */
    public String getNome() {
        return nome;
    }

}