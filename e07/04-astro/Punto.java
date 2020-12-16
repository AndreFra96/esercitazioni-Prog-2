import java.util.Objects;

/**
 * Punto rappresenta un punto nello spazio
 * 
 * Funzione di astrazione: AF(x,y,z) = punto nello spazio di coordinate (x,y,z)
 * Invariante di rappresentazione: qualsiasi combinazione di tre numeri interi
 * rappresenta un punto valido nello spazio
 * 
 */
public class Punto {
    private final int x;
    private final int y;
    private final int z;

    /**
     * Inizializza un nuovo Punto con le coordinate in input
     * @param x coordinata x
     * @param y coordinata y
     * @param z coordinata z
     */
    public Punto(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Restituisce un nuovo punto, corrispondente al punto ottenuto
     * sommando le coordinate del punto attuale con quelle del punto in input
     * 
     * @param punto il Punto con il quale sommare this
     * @return un nuovo punto di coordinate: (this.x + punto.x , this.y + punto.y, this.z + punto.z)
     * @throws NullPointerException se il punto in input è null
     */
    public Punto somma(Punto punto) {
        Objects.requireNonNull(punto);
        return new Punto(this.getX() + punto.getX(), this.getY() + punto.getY(),
                this.getY() + punto.getY());
    }

    /**
     * Restituisce un nuovo punto, corrispondente al punto ottenuto
     * sottraendo le coordinate del punto in input alle coordinate di this
     * 
     * @param punto il Punto da sottrarre a this
     * @return un nuovo punto di coordinate: (this.x - punto.x , this.y - punto.y, this.z - punto.z)
     * @throws NullPointerException se il punto in input è null
     */
    public Punto sottrazione(Punto punto) {
        Objects.requireNonNull(punto);
        return new Punto(this.getX() - punto.getX(), this.getY() - punto.getY(),
                this.getY() - punto.getY());
    }

    /**
     * Restituisce la norma di this
     * @return norma
     */
    public int norma() {
        int norma = this.getX() > 0 ? this.getX() : -this.getX();
        norma += this.getY() > 0 ? this.getY() : -this.getY();
        norma += this.getZ() > 0 ? this.getZ() : -this.getZ();
        return norma;
    }

    /**
     * Restituisce la x della coordinata this
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Restituisce la coordinata y del punto attuale
     * @return coordinata y
     */
    public int getY() {
        return y;
    }

    /**
     * Restituisce la coordinata z del punto attuale
     * @return coordinata z
     */
    public int getZ() {
        return z;
    }

    /**
     * Restituisce una stringa contenente le coordinate del punto
     */
    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ")";
    }

    @Override
    public int hashCode() {
        int hash = Integer.hashCode(getX());
        hash = 31 * hash + Integer.hashCode(getY());
        hash = 31 * hash + Integer.hashCode(getZ());
        return hash;
    }

    /**
     * Due punti sono uguali quando hanno tutte le stesse coordinate
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Punto))
            return false;
        Punto actual = (Punto) obj;
        return (actual.getX() == this.getX() && actual.getY() == this.getY() && actual.getZ() == this.getZ());
    }
}