package by.javatr.financetracker.dao.exception;

public class FailedDeleteException extends DAOException {
    public FailedDeleteException() {
    }

    public FailedDeleteException(String message) {
        super(message);
    }

    public FailedDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedDeleteException(Throwable cause) {
        super(cause);
    }
}
