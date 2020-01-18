package by.javatr.financetracker.service.exception;

public class LogInInvalidCharactersExceptions extends ClientServiceException {
    public LogInInvalidCharactersExceptions() {
    }

    public LogInInvalidCharactersExceptions(String message) {
        super(message);
    }

    public LogInInvalidCharactersExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public LogInInvalidCharactersExceptions(Throwable cause) {
        super(cause);
    }
}
