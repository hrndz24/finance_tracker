package by.javatr.financetracker.dao.exception;

public class FailedEditUserInfoException extends Exception {
    public FailedEditUserInfoException() {
    }

    public FailedEditUserInfoException(String message) {
        super(message);
    }

    public FailedEditUserInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedEditUserInfoException(Throwable cause) {
        super(cause);
    }
}
