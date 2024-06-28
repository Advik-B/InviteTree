package dev.advik.invitetree.exceptions;

public class PasswordMismatch extends Exception {
    public PasswordMismatch(String message) {
        super(message);
    }

    public PasswordMismatch(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMismatch(Throwable cause) {
        super(cause);
    }

    public PasswordMismatch() {
        super();
    }

}
