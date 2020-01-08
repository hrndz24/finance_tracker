package by.javatr.financetracker.view;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.bean.ExpenseCategory;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.exception.*;
import by.javatr.financetracker.dao.impl.FileAccountDAO;
import by.javatr.financetracker.service.WeakPasswordException;
import by.javatr.financetracker.dao.impl.FileUserDAO;

import java.math.BigDecimal;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws FailedAddUserException, WeakPasswordException, FailedFindUserException, UserNotFoundException, UserDAOException, FailedAddAccountException, FailedEditAccountException, AccountNotFoundException, FailedDeleteAccountException, AccountDAOException {
        /*
        BigDecimal bigDecimal = new BigDecimal(12.20);
        Account account = new Account();
        Date date = new Date();
        Income expense = new Income(bigDecimal, IncomeCategory.SALARY, account, date);
        Income expense1 = new Income(bigDecimal, IncomeCategory.SALARY, account, date);
        System.out.println(expense.hashCode()==expense1.hashCode());
        System.out.println(expense.equals(expense1));
         */
        //System.out.println(new Date().hashCode());
        FileUserDAO fileUserDAO = new FileUserDAO("users.txt", ", ");
        //fileUserDAO.addUser(new User("bdsfdgfhh"), new char[]{'f', 'd'});
        //System.out.println(fileUserDAO.hasUser("mdsfdgfhh", new char[]{'b', 'a'}));
        //fileUserDAO.deleteUser(new User("mdsfdgfhh"));
        //fileUserDAO.editLogIn("sdsfdgfhh", "natik");
        //fileUserDAO.editPassword("jdsfdgfhh", new char[]{'n', 'a'});
        //System.out.println(fileUserDAO.getUser("fdsfdgfhh"));

        FileAccountDAO fileAccountDAO = new FileAccountDAO("accounts.txt", ", ");
        //fileAccountDAO.addAccount(new User("nit@nat.com"), new Account("student card", new BigDecimal(24.25)));
        Account account = new Account("natik card", new BigDecimal(24.25), -2041586231);
        //account.setBalance(new BigDecimal(18.35));
        //account.setName("not a student anymore card");
        //fileAccountDAO.editAccount(fileUserDAO.getUser("pak@nat.com"), account);
        //fileAccountDAO.deleteAccount(fileUserDAO.getUser("pak@nat.com"), account);
        //fileAccountDAO.addAccount(new User("hashick"), account);
        //System.out.println(fileAccountDAO.getAllAccounts(fileUserDAO.getUser("pak@nat.com")));
        //System.out.println(fileAccountDAO.getAllAccounts(new User("j")));
        //System.out.println(ExpenseCategory.valueOf("BILLS"));
        //System.out.println(new Date());
    }
}
