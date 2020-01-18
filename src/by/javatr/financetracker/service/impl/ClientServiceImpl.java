package by.javatr.financetracker.service.impl;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.IncomeDAO;
import by.javatr.financetracker.dao.UserDAO;
import by.javatr.financetracker.dao.exception.DAOException;
import by.javatr.financetracker.dao.factory.DAOFactory;
import by.javatr.financetracker.service.ClientService;
import by.javatr.financetracker.service.exception.*;

import java.math.BigDecimal;

public class ClientServiceImpl implements ClientService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private AccountDAO accountDAO = daoFactory.getAccountDAO();
    private ExpenseDAO expenseDAO = daoFactory.getExpenseDAO();
    private IncomeDAO incomeDAO = daoFactory.getIncomeDAO();
    private UserDAO userDAO = daoFactory.getUserDao();

    @Override
    public User signUp(String logIn, char[] password) throws ClientServiceException {
        if (logIn == null || logIn.isEmpty()) {
            //TODO create message
            throw new NullLogInException();
        }

        if (isWeakPassword(password)) {
            //TODO create message
            throw new WeakPasswordException();
        }

        if (!isValidPassword(password)) {
            //TODO create message
            throw new InvalidPasswordException();
        }

        if (!isValidLogIn(logIn)) {
            //TODO create message
            throw new LogInInvalidCharactersExceptions();
        }

        //TODO в property file создать строку с символами, которые нельзя использовать

        try {
            if (userDAO.hasUser(logIn)) {
                //TODO create message
                throw new LogInTakenException();
            }
        } catch (DAOException e) {
            //TODO create message
            throw new ClientServiceException(e);
        }

        User user = new User(logIn);
        try {
            userDAO.addUser(user, password);
        } catch (DAOException e) {
            //TODO create message
            throw new ClientServiceException(e);
        }

        Account account = new Account("Cash", new BigDecimal(0.0));
        try {
            accountDAO.addAccount(user, account);
        } catch (DAOException e) {
            //TODO create message
            throw new ClientServiceException(e);
        }
        return user;
    }

    @Override
    public User logIn(String logIn, char[] password) throws ClientServiceException {
        try {
            if (userDAO.hasUser(logIn, password)) {
                return userDAO.getUser(logIn);
            } else {
                //TODO create message
                throw new LogInFailedException("Failed to log in");
            }
        } catch (DAOException e) {
            //TODO create message
            throw new ClientServiceException(e);
        }
    }

    @Override
    public void deactivateAccount(User user, char[] password) throws ClientServiceException {
        try {
            if (userDAO.hasUser(user.getLogIn())) {
                userDAO.deleteUser(user);
            } else {
                //TODO create message
                throw new FailedDeactivateAccount();
            }
        } catch (DAOException e) {
            //TODO create message
            throw new ClientServiceException(e);
        }
    }

    @Override
    public void editLogIn(User user, String newLogIn) throws ClientServiceException {
        if(!isValidLogIn(newLogIn)){
            //TODO create message
            throw new LogInInvalidCharactersExceptions();
        }

        try {
            if(userDAO.hasUser(user.getLogIn())){
                userDAO.editLogIn(user, newLogIn);
            } else{
                //TODO create message
                throw new FailedEditLogInException();
            }
        } catch (DAOException e) {
            //TODO create message
            throw new ClientServiceException(e);
        }
    }

    @Override
    public void changePassword(User user, char[] oldPassword, char[] newPassword) throws ClientServiceException {
        if (isWeakPassword(newPassword)) {
            //TODO create message
            throw new WeakPasswordException();
        }
        if (!isValidPassword(newPassword)) {
            //TODO create message
            throw new InvalidPasswordException();
        }

        try {
            if (userDAO.hasUser(user.getLogIn(), oldPassword)) {
                userDAO.editPassword(user, newPassword);
            } else {
                //TODO create message
                throw new FailedChangePassword();
            }
        } catch (DAOException e) {
            //TODO create message
            throw new ClientServiceException(e);
        }
    }

    private boolean isValidLogIn(String logIn){
        //TODO replace literals
        return  !(logIn.contains(", ") || logIn.contains(" "));
    }

    private boolean isValidPassword(char[] password) {
        //TODO replace literals
        for (char c : password) {
            if (c == ' ' || c == ',') {
                return false;
            }
        }
        return true;
    }

    private boolean isWeakPassword(char[] password) {
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
