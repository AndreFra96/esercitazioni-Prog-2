@SuppressWarnings("serial")
public class ZeroDivisorException extends RuntimeException{
    public ZeroDivisorException() {
        super();
    }

    public ZeroDivisorException(String msg) {
        super(msg);
    }
}