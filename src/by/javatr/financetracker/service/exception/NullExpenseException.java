package by.javatr.financetracker.service.exception;

public class NullExpenseException extends FinanceTrackerServiceException {
    public NullExpenseException() {
    }

    public NullExpenseException(String message) {
        super(message);
    }

    public NullExpenseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullExpenseException(Throwable cause) {
        super(cause);
    }
}
