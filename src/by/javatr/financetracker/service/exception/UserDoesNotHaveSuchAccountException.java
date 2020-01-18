package by.javatr.financetracker.service.exception;

public class UserDoesNotHaveSuchAccountException extends FinanceTrackerServiceException {
    public UserDoesNotHaveSuchAccountException() {
    }

    public UserDoesNotHaveSuchAccountException(String message) {
        super(message);
    }

    public UserDoesNotHaveSuchAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesNotHaveSuchAccountException(Throwable cause) {
        super(cause);
    }
}
