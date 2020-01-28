package by.javatr.financetracker.service.exception;

public class FinanceTrackerServiceException extends Exception {
    public FinanceTrackerServiceException() {
    }

    public FinanceTrackerServiceException(String message) {
        super(message);
    }

    public FinanceTrackerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FinanceTrackerServiceException(Throwable cause) {
        super(cause);
    }
}
