package by.javatr.financetracker.dao.exception;

public class FailedEditInfoException extends DAOException {
    public FailedEditInfoException() {
    }

    public FailedEditInfoException(String message) {
        super(message);
    }

    public FailedEditInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedEditInfoException(Throwable cause) {
        super(cause);
    }
}
