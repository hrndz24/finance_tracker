package by.javatr.financetracker.service.exception;

public class NullNoteException extends FinanceTrackerServiceException {
    public NullNoteException() {
    }

    public NullNoteException(String message) {
        super(message);
    }

    public NullNoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullNoteException(Throwable cause) {
        super(cause);
    }
}
