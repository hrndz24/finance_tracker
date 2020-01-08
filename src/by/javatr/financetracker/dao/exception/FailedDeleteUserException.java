package by.javatr.financetracker.dao.exception;

public class FailedDeleteUserException extends Exception {
    public FailedDeleteUserException() {
    }

    public FailedDeleteUserException(String message) {
        super(message);
    }

    public FailedDeleteUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedDeleteUserException(Throwable cause) {
        super(cause);
    }
}
