package by.javatr.financetracker.service.exception;

public class InvalidTransactionDateException extends FinanceTrackerServiceException {
    public InvalidTransactionDateException() {
    }

    public InvalidTransactionDateException(String message) {
        super(message);
    }

    public InvalidTransactionDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTransactionDateException(Throwable cause) {
        super(cause);
    }
}
