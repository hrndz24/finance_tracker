package by.javatr.financetracker.dao.factory;

import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.IncomeDAO;
import by.javatr.financetracker.dao.UserDAO;
import by.javatr.financetracker.dao.impl.FileAccountDAO;
import by.javatr.financetracker.dao.impl.FileExpenseDAO;
import by.javatr.financetracker.dao.impl.FileIncomeDAO;
import by.javatr.financetracker.dao.impl.FileUserDAO;
import by.javatr.financetracker.dao.stringvalues.StringProperty;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final String dataSeparator = StringProperty.getStringValue("dataSeparator");
    private final UserDAO fileUserDAOImpl = new FileUserDAO(StringProperty.getStringValue("usersFilePath"), dataSeparator);
    private final AccountDAO fileAccountDAOImpl = new FileAccountDAO(StringProperty.getStringValue("accountsFilePath"), dataSeparator);
    private final ExpenseDAO fileExpenseDAOImpl = new FileExpenseDAO(StringProperty.getStringValue("transactionsFilePath"), dataSeparator);
    private final IncomeDAO fileIncomeDAOImpl = new FileIncomeDAO(StringProperty.getStringValue("transactionsFilePath"), dataSeparator);

    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDao() {
        return fileUserDAOImpl;
    }

    public AccountDAO getAccountDAO() {
        return fileAccountDAOImpl;
    }

    public ExpenseDAO getExpenseDAO() {
        return fileExpenseDAOImpl;
    }

    public IncomeDAO getIncomeDAO() {
        return fileIncomeDAOImpl;
    }
}
