package by.javatr.financetracker.dao.exception;

public class FailedAddException extends DAOException {

    public FailedAddException(){
        super();
    }

    public FailedAddException(String message) {
        super(message);
    }

    public FailedAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedAddException(Throwable cause) {
        super(cause);
    }
}
