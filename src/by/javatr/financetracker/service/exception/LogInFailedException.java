package by.javatr.financetracker.service.exception;

public class LogInFailedException extends ClientServiceException{
    public LogInFailedException() {
    }

    public LogInFailedException(String message) {
        super(message);
    }

    public LogInFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogInFailedException(Throwable cause) {
        super(cause);
    }
}
