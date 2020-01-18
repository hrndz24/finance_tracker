package by.javatr.financetracker.service.exception;

public class NullUserException extends FinanceTrackerServiceException {
    public NullUserException() {
    }

    public NullUserException(String message) {
        super(message);
    }

    public NullUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullUserException(Throwable cause) {
        super(cause);
    }
}
