package by.javatr.financetracker.service.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

public class FinanceTrackerServiceValidatorTest {

    private FinanceTrackerServiceValidator validator;

    @Before
    public void initValidator(){
        validator = new FinanceTrackerServiceValidator();
    }

    @Test
    public void isValidTransactionSumNullSum() {
        Assert.assertFalse(validator.isValidTransactionSum(null));
    }

    @Test
    public void isValidTransactionSumPositiveSum() {
        Assert.assertTrue(validator.isValidTransactionSum(new BigDecimal(24.24)));
    }

    @Test
    public void isValidTransactionSumNegativeSum() {
        Assert.assertFalse(validator.isValidTransactionSum(new BigDecimal(-24.24)));
    }

    @Test
    public void isValidTransactionSumZeroSum() {
        Assert.assertFalse(validator.isValidTransactionSum(new BigDecimal(0)));
    }

    @Test
    public void isValidTransactionDateNullDate() {
        Assert.assertFalse(validator.isValidTransactionDate(null));
    }

    @Test
    public void isValidTransactionDateAfterToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        Assert.assertFalse(validator.isValidTransactionDate(calendar.getTime()));
    }

    @Test
    public void isValidTransactionDateBeforeToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        Assert.assertTrue(validator.isValidTransactionDate(calendar.getTime()));
    }

    @Test
    public void isValidNoteNullNote() {
        Assert.assertFalse(validator.isValidNote(null));
    }

    @Test
    public void isValidNoteStringNullAsNote() {
        Assert.assertFalse(validator.isValidNote("null"));
    }

    @Test
    public void isValidNoteCorrectNote() {
        Assert.assertTrue(validator.isValidNote("Poor_but_stunning"));
    }

    @Test
    public void isValidAccountNameNullName() {
        Assert.assertFalse(validator.isValidAccountName(null));
    }

    @Test
    public void isValidAccountNameEmptyString() {
        Assert.assertFalse(validator.isValidAccountName(""));

    }

    @Test
    public void isValidAccountNameCorrectName() {
        Assert.assertTrue(validator.isValidAccountName("Golden_card"));
    }
}