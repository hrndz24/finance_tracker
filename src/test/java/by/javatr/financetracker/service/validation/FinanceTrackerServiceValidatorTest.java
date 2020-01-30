package by.javatr.financetracker.service.validation;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

public class FinanceTrackerServiceValidatorTest {

    @Test
    public void isValidTransactionSumNullSum() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionSum(null));
    }

    @Test
    public void isValidTransactionSumPositiveSum() {
        Assert.assertTrue(FinanceTrackerServiceValidator.isValidTransactionSum(new BigDecimal(24.24)));
    }

    @Test
    public void isValidTransactionSumNegativeSum() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionSum(new BigDecimal(-24.24)));
    }

    @Test
    public void isValidTransactionSumZeroSum() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionSum(new BigDecimal(0)));
    }

    @Test
    public void isValidTransactionDateNullDate() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionDate(null));
    }

    @Test
    public void isValidTransactionDateAfterToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionDate(calendar.getTime()));
    }

    @Test
    public void isValidTransactionDateBeforeToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        Assert.assertTrue(FinanceTrackerServiceValidator.isValidTransactionDate(calendar.getTime()));
    }

    @Test
    public void isValidNoteNullNote() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidNote(null));
    }

    @Test
    public void isValidNoteStringNullAsNote() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidNote("null"));
    }

    @Test
    public void isValidNoteCorrectNote() {
        Assert.assertTrue(FinanceTrackerServiceValidator.isValidNote("Poor_but_stunning"));
    }

    @Test
    public void isValidAccountNameNullName() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidAccountName(null));
    }

    @Test
    public void isValidAccountNameEmptyString() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidAccountName(""));

    }

    @Test
    public void isValidAccountNameCorrectName() {
        Assert.assertTrue(FinanceTrackerServiceValidator.isValidAccountName("Golden_card"));
    }
}