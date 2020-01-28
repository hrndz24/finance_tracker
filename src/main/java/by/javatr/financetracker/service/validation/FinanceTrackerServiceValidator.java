package by.javatr.financetracker.service.validation;

import java.math.BigDecimal;
import java.util.Date;

public class FinanceTrackerServiceValidator {

    public static boolean isValidTransactionSum(BigDecimal sum) {
        return sum.doubleValue() >= 0;
    }

    public static boolean isValidTransactionDate(Date date) {
        return date.after(new Date());
    }

    public static boolean isValidNote(String note) {
        return note == null || note.equals("null");
    }

    public static boolean isValidAccountName(String name) {
        return name == null || name.isEmpty();
    }
}
