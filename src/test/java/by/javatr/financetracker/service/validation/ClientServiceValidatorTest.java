package by.javatr.financetracker.service.validation;

import org.junit.Assert;
import org.junit.Test;

public class ClientServiceValidatorTest {

    @Test
    public void isValidLogInCorrectLogIn() {
        Assert.assertTrue(ClientServiceValidator.isValidLogIn("nat@nat.com"));
    }

    @Test
    public void isValidLogInWithComma() {
        Assert.assertFalse(ClientServiceValidator.isValidLogIn("nat,com"));
    }

    @Test
    public void isValidLogInWithSpace() {
        Assert.assertFalse(ClientServiceValidator.isValidLogIn("nat com"));
    }

    @Test
    public void isValidPasswordCorrectPassword() {
        char[] password = "sillegalanoche".toCharArray();
        Assert.assertTrue(ClientServiceValidator.isValidPassword(password));
    }

    @Test
    public void isValidPasswordWithComma() {
        char[] password = "sillega,lanoche".toCharArray();
        Assert.assertFalse(ClientServiceValidator.isValidPassword(password));
    }

    @Test
    public void isValidPasswordWithSpace() {
        char[] password = "sillega lanoche".toCharArray();
        Assert.assertFalse(ClientServiceValidator.isValidPassword(password));
    }

    @Test
    public void isWeakPasswordShortPassword() {
        char[] password = "1234".toCharArray();
        Assert.assertTrue(ClientServiceValidator.isWeakPassword(password));
    }

    @Test
    public void isWeakPasswordWithoutCapitalLetter() {
        char[] password = "nosequeescribiraqui".toCharArray();
        Assert.assertTrue(ClientServiceValidator.isWeakPassword(password));
    }

    @Test
    public void isWeakPasswordStrongPassword() {
        char[] password = "esperoQueloesbueno".toCharArray();
        Assert.assertFalse(ClientServiceValidator.isWeakPassword(password));
    }
}