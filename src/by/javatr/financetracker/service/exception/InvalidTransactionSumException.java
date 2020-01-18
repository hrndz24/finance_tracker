package by.javatr.financetracker.service.exception;

public class InvalidTransactionSumException extends FinanceTrackerServiceException {
    public InvalidTransactionSumException() {
    }

    public InvalidTransactionSumException(String message) {
        super(message);
    }

    public InvalidTransactionSumException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTransactionSumException(Throwable cause) {
        super(cause);
    }
}
