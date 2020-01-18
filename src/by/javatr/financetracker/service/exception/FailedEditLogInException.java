package by.javatr.financetracker.service.exception;

public class FailedEditLogInException extends ClientServiceException {
    public FailedEditLogInException() {
    }

    public FailedEditLogInException(String message) {
        super(message);
    }

    public FailedEditLogInException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedEditLogInException(Throwable cause) {
        super(cause);
    }
}
