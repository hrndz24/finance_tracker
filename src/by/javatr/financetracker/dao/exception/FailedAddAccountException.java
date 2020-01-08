package by.javatr.financetracker.dao.exception;

public class FailedAddAccountException extends Exception {
    public FailedAddAccountException() {
    }

    public FailedAddAccountException(String message) {
        super(message);
    }

    public FailedAddAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedAddAccountException(Throwable cause) {
        super(cause);
    }
}
