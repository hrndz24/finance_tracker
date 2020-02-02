package by.javatr.financetracker.service.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientServiceValidatorTest {

    private ClientServiceValidator validator;

    @Before
    public void initValidator(){
        validator = new ClientServiceValidator();
    }

    @Test
    public void isValidLogInCorrectLogIn() {
        Assert.assertTrue(validator.isValidLogIn("nat@nat.com"));
    }

    @Test
    public void isValidLogInWithComma() {
        Assert.assertFalse(validator.isValidLogIn("nat,com"));
    }

    @Test
    public void isValidLogInWithSpace() {
        Assert.assertFalse(validator.isValidLogIn("nat com"));
    }

    @Test
    public void isValidPasswordCorrectPassword() {
        char[] password = "sillegalanoche".toCharArray();
        Assert.assertTrue(validator.isValidPassword(password));
    }

    @Test
    public void isValidPasswordWithComma() {
        char[] password = "sillega,lanoche".toCharArray();
        Assert.assertFalse(validator.isValidPassword(password));
    }

    @Test
    public void isValidPasswordWithSpace() {
        char[] password = "sillega lanoche".toCharArray();
        Assert.assertFalse(validator.isValidPassword(password));
    }

    @Test
    public void isWeakPasswordShortPassword() {
        char[] password = "1234".toCharArray();
        Assert.assertTrue(validator.isWeakPassword(password));
    }

    @Test
    public void isWeakPasswordWithoutCapitalLetter() {
        char[] password = "nosequeescribiraqui".toCharArray();
        Assert.assertTrue(validator.isWeakPassword(password));
    }

    @Test
    public void isWeakPasswordStrongPassword() {
        char[] password = "esperoQueloesbueno".toCharArray();
        Assert.assertFalse(validator.isWeakPassword(password));
    }
}