package by.javatr.financetracker.service.exception;

public class NullLogInException extends ClientServiceException {
    public NullLogInException() {
    }

    public NullLogInException(String message) {
        super(message);
    }

    public NullLogInException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullLogInException(Throwable cause) {
        super(cause);
    }
}
