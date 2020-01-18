package by.javatr.financetracker.dao.exception;

public class ExpenseNotFoundException extends DAOException {
    public ExpenseNotFoundException() {
    }

    public ExpenseNotFoundException(String message) {
        super(message);
    }

    public ExpenseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpenseNotFoundException(Throwable cause) {
        super(cause);
    }
}
