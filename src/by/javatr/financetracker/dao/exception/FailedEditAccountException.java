package by.javatr.financetracker.dao.exception;

public class FailedEditAccountException extends DAOException{
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
