package pet.store;

public class dbexception extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public dbexception(String message) {
        super(message);
    }

    public dbexception(Throwable cause) {
        super(cause);
    }

    public dbexception(String message, Throwable cause) {
        super(message, cause);
    }
}
