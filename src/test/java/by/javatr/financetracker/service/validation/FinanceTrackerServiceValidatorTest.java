package by.javatr.financetracker.service.validation;

import org.junit.Assert;

import java.math.BigDecimal;
import java.util.Calendar;

public class FinanceTrackerServiceValidatorTest {

    @org.junit.Test
    public void isValidTransactionSumNullSum() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionSum(null));
    }

    @org.junit.Test
    public void isValidTransactionSumPositiveSum() {
        Assert.assertTrue(FinanceTrackerServiceValidator.isValidTransactionSum(new BigDecimal(24.24)));
    }

    @org.junit.Test
    public void isValidTransactionSumNegativeSum() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionSum(new BigDecimal(-24.24)));
    }

    @org.junit.Test
    public void isValidTransactionSumZeroSum() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionSum(new BigDecimal(0)));
    }

    @org.junit.Test
    public void isValidTransactionDateNullDate() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionDate(null));
    }

    @org.junit.Test
    public void isValidTransactionDateAfterToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidTransactionDate(calendar.getTime()));
    }

    @org.junit.Test
    public void isValidTransactionDateBeforeToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
        Assert.assertTrue(FinanceTrackerServiceValidator.isValidTransactionDate(calendar.getTime()));
    }

    @org.junit.Test
    public void isValidNoteNullNote() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidNote(null));
    }

    @org.junit.Test
    public void isValidNoteStringNullAsNote() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidNote("null"));
    }

    @org.junit.Test
    public void isValidNoteRightNote() {
        Assert.assertTrue(FinanceTrackerServiceValidator.isValidNote("Poor_but_stunning"));
    }

    @org.junit.Test
    public void isValidAccountNameNullName() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidAccountName(null));
    }

    @org.junit.Test
    public void isValidAccountNameEmptyString() {
        Assert.assertFalse(FinanceTrackerServiceValidator.isValidAccountName(""));

    }

    @org.junit.Test
    public void isValidAccountNameRightName() {
        Assert.assertTrue(FinanceTrackerServiceValidator.isValidAccountName("Golden_card"));
    }
}