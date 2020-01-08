package by.javatr.financetracker.dao.exception;

public class FailedEditAccountException extends Exception{
    public FailedEditAccountException() {
    }

    public FailedEditAccountException(String message) {
        super(message);
    }

    public FailedEditAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedEditAccountException(Throwable cause) {
        super(cause);
    }
}
