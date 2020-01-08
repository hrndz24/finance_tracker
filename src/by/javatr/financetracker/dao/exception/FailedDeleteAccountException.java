package by.javatr.financetracker.dao.exception;

public class FailedDeleteAccountException extends Exception {
    public FailedDeleteAccountException() {
    }

    public FailedDeleteAccountException(String message) {
        super(message);
    }

    public FailedDeleteAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedDeleteAccountException(Throwable cause) {
        super(cause);
    }
}
