package by.javatr.financetracker.service.impl;

import by.javatr.financetracker.entity.Account;
import by.javatr.financetracker.entity.Expense;
import by.javatr.financetracker.entity.Income;
import by.javatr.financetracker.entity.User;
import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.IncomeDAO;
import by.javatr.financetracker.dao.UserDAO;
import by.javatr.financetracker.dao.exception.DAOException;
import by.javatr.financetracker.dao.factory.DAOFactory;
import by.javatr.financetracker.service.ClientService;
import by.javatr.financetracker.service.exception.ClientServiceException;
import by.javatr.financetracker.service.constants.StringProperty;
import by.javatr.financetracker.service.validation.ClientServiceValidator;

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
            throw new ClientServiceException("Null logIn.");
        }

        if (password == null) {
            throw new ClientServiceException("Null password.");
        }

        if (ClientServiceValidator.isWeakPassword(password)) {
            throw new ClientServiceException("Weak password.");
        }

        if (!ClientServiceValidator.isValidPassword(password)) {
            throw new ClientServiceException("Invalid password characters.");
        }

        if (!ClientServiceValidator.isValidLogIn(logIn)) {
            throw new ClientServiceException("Invalid logIn characters.");
        }

        try {
            if (userDAO.hasUser(logIn)) {
                throw new ClientServiceException("LogIn is already taken.");
            }
        } catch (DAOException e) {
            throw new ClientServiceException(e);
        }

        User user = new User(logIn);
        try {
            userDAO.addUser(user, password);
        } catch (DAOException e) {
            throw new ClientServiceException(e);
        }

        Account account = new Account(StringProperty.getStringValue("firstDefaultAccountName"), new BigDecimal(0.0));
        try {
            accountDAO.addAccount(user.getId(), account);
        } catch (DAOException e) {
            throw new ClientServiceException("Failed to access users' data.", e);
        }
        return user;
    }

    @Override
    public User logIn(String logIn, char[] password) throws ClientServiceException {
        if (logIn == null || logIn.isEmpty()) {
            throw new ClientServiceException("Null logIn.");
        }
        if (password == null) {
            throw new ClientServiceException("Null password.");
        }
        try {
            if (userDAO.hasUser(logIn, password)) {
                return userDAO.getUser(logIn);
            } else {
                throw new ClientServiceException("User with such logIn and password not found.");
            }
        } catch (DAOException e) {
            throw new ClientServiceException("Failed to access users' data.", e);
        }
    }

    @Override
    public void deactivateAccount(User user, char[] password) throws ClientServiceException {
        if (user == null) {
            throw new ClientServiceException("Null user.");
        }
        if (password == null) {
            throw new ClientServiceException("Null password.");
        }

        try {
            if (userDAO.hasUser(user.getLogIn(), password)) {
                userDAO.deleteUser(user.getId());
            } else {
                throw new ClientServiceException("User with such logIn and password not found.");
            }

            Account[] accounts = accountDAO.getAllAccounts(user.getId());
            for (Account account : accounts) {
                accountDAO.deleteAccount(user.getId(), account.getId());
            }

            Expense[] expenses = expenseDAO.getAllExpenses(user.getId());
            for (Expense expense : expenses) {
                expenseDAO.deleteExpense(user.getId(), expense.getId());
            }

            Income[] incomes = incomeDAO.getAllIncomes(user.getId());
            for (Income income : incomes) {
                incomeDAO.deleteIncome(user.getId(), income.getId());
            }

        } catch (DAOException e) {
            throw new ClientServiceException("Failed to access users' data.", e);
        }
    }

    @Override
    public void editLogIn(User user, String newLogIn) throws ClientServiceException {
        if (user == null) {
            throw new ClientServiceException("Null user.");
        }
        if (newLogIn == null || newLogIn.isEmpty()) {
            throw new ClientServiceException("Null new logIn.");
        }
        if (!ClientServiceValidator.isValidLogIn(newLogIn)) {
            throw new ClientServiceException("LogIn contains invalid characters");
        }

        try {
            if (!userDAO.hasUser(user.getLogIn())) {
                throw new ClientServiceException("No user with such logIn found");
            }
            if (userDAO.hasUser(newLogIn)) {
                throw new ClientServiceException("LogIn taken");
            }
            userDAO.editLogIn(user.getId(), newLogIn);
        } catch (DAOException e) {
            throw new ClientServiceException("Failed to access users' data.", e);
        }
    }

    @Override
    public void changePassword(User user, char[] oldPassword, char[] newPassword) throws ClientServiceException {
        if (user == null) {
            throw new ClientServiceException("Null user.");
        }
        if (oldPassword == null || newPassword == null) {
            throw new ClientServiceException("Null password.");
        }
        if (ClientServiceValidator.isWeakPassword(newPassword)) {
            throw new ClientServiceException("Weak password.");
        }
        if (!ClientServiceValidator.isValidPassword(newPassword)) {
            throw new ClientServiceException("Password contains invalid characters.");
        }

        try {
            if (userDAO.hasUser(user.getLogIn(), oldPassword)) {
                userDAO.editPassword(user.getId(), newPassword);
            } else {
                throw new ClientServiceException("User with such logIn and password not found");
            }
        } catch (DAOException e) {
            throw new ClientServiceException("Failed to access users' data.", e);
        }
    }

}
