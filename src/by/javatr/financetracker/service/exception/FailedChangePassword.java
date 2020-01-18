package by.javatr.financetracker.service.exception;

public class FailedChangePassword extends ClientServiceException {
    public FailedChangePassword() {
    }

    public FailedChangePassword(String message) {
        super(message);
    }

    public FailedChangePassword(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedChangePassword(Throwable cause) {
        super(cause);
    }
}
