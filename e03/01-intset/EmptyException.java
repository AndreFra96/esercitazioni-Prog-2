//OVERVIEW: Eccezione sollevata in caso di insieme vuoto
public class EmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmptyException() {
        super();
    }

    public EmptyException(String msg) {
        super(msg);
    }
}