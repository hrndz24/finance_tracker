package by.javatr.financetracker.dao.exception;

public class IncomeNotFoundException extends DAOException{
    public IncomeNotFoundException() {
    }

    public IncomeNotFoundException(String message) {
        super(message);
    }

    public IncomeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncomeNotFoundException(Throwable cause) {
        super(cause);
    }
}
