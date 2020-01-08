package by.javatr.financetracker.dao.exception;

public class FailedAddUserException extends Exception {

    public FailedAddUserException(){
        super();
    }

    public FailedAddUserException(String message) {
        super(message);
    }

    public FailedAddUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedAddUserException(Throwable cause) {
        super(cause);
    }
}
