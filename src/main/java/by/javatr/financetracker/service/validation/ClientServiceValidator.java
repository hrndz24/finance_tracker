package by.javatr.financetracker.service.validation;

import by.javatr.financetracker.service.stringvalues.StringProperty;

public class ClientServiceValidator {
    public static boolean isValidLogIn(String logIn) {
        String invalidCharacters = StringProperty.getStringValue("invalidLogInCharacters");
        for (char c : logIn.toCharArray()) {
            if (invalidCharacters.indexOf(c) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidPassword(char[] password) {
        String invalidCharacters = StringProperty.getStringValue("invalidLogInCharacters");
        for (char c : password) {
            if (invalidCharacters.indexOf(c) != -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWeakPassword(char[] password) {
        if (password.length < 8) {
            return true;
        }
        for (char character : password) {
            if (character >= 65 && character <= 90) {
                return false;
            }
        }
        return true;
    }
}
