package it.pierfani.firebaseappcheck;

public class FirebaseAppCheckException extends RuntimeException {
    private static final long serialVersionUID = -7043906620236176552L;

    public FirebaseAppCheckException(String message) {
        super(message);
    }

    public FirebaseAppCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}