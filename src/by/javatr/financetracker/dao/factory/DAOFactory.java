package by.javatr.financetracker.dao.factory;

import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.IncomeDAO;
import by.javatr.financetracker.dao.UserDAO;
import by.javatr.financetracker.dao.impl.FileAccountDAO;
import by.javatr.financetracker.dao.impl.FileExpenseDAO;
import by.javatr.financetracker.dao.impl.FileIncomeDAO;
import by.javatr.financetracker.dao.impl.FileUserDAO;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO fileUserDAOImpl = new FileUserDAO("users.txt", ", ");
    private final AccountDAO fileAccountDAOImpl = new FileAccountDAO("accounts.txt", ", ");
    private final ExpenseDAO fileExpenseDAOImpl = new FileExpenseDAO("transactions.txt", ", ");
    private final IncomeDAO fileIncomeDAOImpl = new FileIncomeDAO("transactions.txt", ", ");

    private DAOFactory(){

    }

    public static DAOFactory getInstance(){
        return instance;
    }
    public UserDAO getUserDao() {
        return fileUserDAOImpl;
    }

    public AccountDAO getAccountDAO(){
        return fileAccountDAOImpl;
    }

    public ExpenseDAO getExpenseDAO(){
        return fileExpenseDAOImpl;
    }

    public IncomeDAO getIncomeDAO(){
        return fileIncomeDAOImpl;
    }
}
