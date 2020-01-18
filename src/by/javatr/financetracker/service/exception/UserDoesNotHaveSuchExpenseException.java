package by.javatr.financetracker.service.exception;

public class UserDoesNotHaveSuchExpenseException extends FinanceTrackerServiceException {
    public UserDoesNotHaveSuchExpenseException() {
    }

    public UserDoesNotHaveSuchExpenseException(String message) {
        super(message);
    }

    public UserDoesNotHaveSuchExpenseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesNotHaveSuchExpenseException(Throwable cause) {
        super(cause);
    }
}
