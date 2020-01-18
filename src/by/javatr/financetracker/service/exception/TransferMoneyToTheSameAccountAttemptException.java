package by.javatr.financetracker.service.exception;

public class TransferMoneyToTheSameAccountAttemptException extends FinanceTrackerServiceException{
    public TransferMoneyToTheSameAccountAttemptException() {
    }

    public TransferMoneyToTheSameAccountAttemptException(String message) {
        super(message);
    }

    public TransferMoneyToTheSameAccountAttemptException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferMoneyToTheSameAccountAttemptException(Throwable cause) {
        super(cause);
    }
}
