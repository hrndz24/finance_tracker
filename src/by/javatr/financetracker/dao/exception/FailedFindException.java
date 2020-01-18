package by.javatr.financetracker.dao.exception;

public class FailedFindException extends DAOException {
    public FailedFindException() {
    }

    public FailedFindException(String message) {
        super(message);
    }

    public FailedFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedFindException(Throwable cause) {
        super(cause);
    }
}
