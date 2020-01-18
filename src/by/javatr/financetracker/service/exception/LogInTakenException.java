package by.javatr.financetracker.service.exception;

public class LogInTakenException extends ClientServiceException {
    public LogInTakenException() {
    }

    public LogInTakenException(String message) {
        super(message);
    }

    public LogInTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogInTakenException(Throwable cause) {
        super(cause);
    }
}
