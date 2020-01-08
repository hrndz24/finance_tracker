package by.javatr.financetracker.dao.exception;

public class AccountDAOException extends Exception {
    public AccountDAOException() {
    }

    public AccountDAOException(String message) {
        super(message);
    }

    public AccountDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountDAOException(Throwable cause) {
        super(cause);
    }
}
