package by.javatr.financetracker.dao.exception;

public class FailedFindUserException extends Exception {
    public FailedFindUserException() {
    }

    public FailedFindUserException(String message) {
        super(message);
    }

    public FailedFindUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedFindUserException(Throwable cause) {
        super(cause);
    }
}
