package by.javatr.financetracker.service.validation;

import java.math.BigDecimal;
import java.util.Date;

public class FinanceTrackerServiceValidator {

    public boolean isValidTransactionSum(BigDecimal sum) {
        return sum != null && sum.doubleValue() > 0;
    }

    public boolean isValidTransactionDate(Date date) {
        return date != null && date.before(new Date());
    }

    public boolean isValidNote(String note) {
        return note != null && !note.equals("null");
    }

    public boolean isValidAccountName(String name) {
        return name != null && !name.isEmpty();
    }
}
