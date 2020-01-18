package by.javatr.financetracker.service.exception;

public class FailedDeactivateAccount extends ClientServiceException {
    public FailedDeactivateAccount() {
    }

    public FailedDeactivateAccount(String message) {
        super(message);
    }

    public FailedDeactivateAccount(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedDeactivateAccount(Throwable cause) {
        super(cause);
    }
}
