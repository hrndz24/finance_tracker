package by.javatr.financetracker.service.impl;

import by.javatr.financetracker.bean.*;
import by.javatr.financetracker.dao.AccountDAO;
import by.javatr.financetracker.dao.ExpenseDAO;
import by.javatr.financetracker.dao.IncomeDAO;
import by.javatr.financetracker.dao.UserDAO;
import by.javatr.financetracker.dao.exception.*;
import by.javatr.financetracker.dao.factory.DAOFactory;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class FinanceTrackerServiceImpl implements FinanceTrackerService {

    private User user;
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private AccountDAO accountDAO = daoFactory.getAccountDAO();
    private ExpenseDAO expenseDAO = daoFactory.getExpenseDAO();
    private IncomeDAO incomeDAO = daoFactory.getIncomeDAO();
    private UserDAO userDAO = daoFactory.getUserDao();

    public FinanceTrackerServiceImpl() {

    }

    public void setUser(User user) throws FinanceTrackerServiceException {
        if (user == null) {
            throw new NullUserException();
        }

        this.user = user;
    }

    public FinanceTrackerServiceImpl(User user) throws FinanceTrackerServiceException {
        if (user == null) {
            throw new NullUserException();
        }

        this.user = user;
    }

    @Override
    public void addExpense(User user, Expense expense) throws FinanceTrackerServiceException {
        if (user == null) {
            throw new NullUserException();
        }

        if (expense == null) {
            //TODO create message
            throw new NullExpenseException();
        }

        if (expense.getSum().doubleValue() < 0) {
            //TODO create message
            throw new InvalidTransactionSumException();
        }

        if (expense.getDate().after(new Date())) {
            //TODO create message
            throw new InvalidTransactionDateException();
        }

        if (expense.getNote().equals("null")) {
            //TODO create message
            throw new NullNoteException("null note");
        }

        try {
            if (!accountDAO.hasAccount(user, expense.getAccountId())) {
                //TODO create message
                throw new UserDoesNotHaveSuchAccountException();
            }
        } catch (DAOException e) {
            //TODO create message
           throw new FinanceTrackerServiceException();
        }

        try {
            expenseDAO.addExpense(user, expense);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to add expense", e);
        }
    }


    @Override
    public void editExpense(User user, Expense editedExpense) throws FinanceTrackerServiceException {
        if (user == null) {
            throw new NullUserException();
        }

        if (!user.hasTransaction(editedExpense.getId())) {
            //TODO create message
            throw new UserDoesNotHaveSuchExpenseException();
        }
        Expense expense = (Expense) user.getTransaction(editedExpense.getId());

        if (editedExpense.getSum().doubleValue() < 0) {
            //TODO create message
            throw new InvalidTransactionSumException();
        }
        BigDecimal sumChange = editedExpense.getSum().subtract(expense.getSum());
        expense.setSum(editedExpense.getSum());

        expense.setCategory(editedExpense.getCategory());

        if (editedExpense.getDate().after(new Date())) {
            //TODO create message
            throw new InvalidTransactionDateException();
        }

        expense.setDate(editedExpense.getDate());

        if (editedExpense.getNote() == null) {
            //TODO create message
            throw new NullNoteException();
        }
        expense.setNote(editedExpense.getNote());

        if (!user.hasAccount(editedExpense.getAccountId())) {
            //TODO create message
            throw new UserDoesNotHaveSuchAccountException();
        }
        expense.setAccount(editedExpense.getAccountId());

        Account expenseAccount = user.getAccount(editedExpense.getAccountId());
        expenseAccount.setBalance(expenseAccount.getBalance().subtract(sumChange));

        try {
            expenseDAO.editExpense(this.user, expense);
        } catch (DAOException e) {
            //TODO create message
            throw new FinanceTrackerServiceException(e);
        }

        try {
            accountDAO.editAccount(this.user, expenseAccount);
        } catch (DAOException e) {
            //TODO create message
            throw new FinanceTrackerServiceException(e);
        }

    }

    @Override
    public void deleteExpense(User user, Expense expense) throws FinanceTrackerServiceException {

    }

    @Override
    public void addIncome(User user, Income income) throws FinanceTrackerServiceException {

    }

    @Override
    public void editIncome(User user, Income newIncome) throws FinanceTrackerServiceException {

    }

    @Override
    public void deleteIncome(User user, Income income) throws FinanceTrackerServiceException {

    }

    @Override
    public void addAccount(User user, Account account) throws FinanceTrackerServiceException {

    }

    @Override
    public void editAccount(User user, Account editedAccount) throws FinanceTrackerServiceException {

    }

    @Override
    public void deleteAccount(User user, Account account) throws FinanceTrackerServiceException {

    }

    @Override
    public ArrayList<Account> getAccounts(User user) throws FinanceTrackerServiceException {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            accounts = accountDAO.getAllAccounts(user);
        } catch (DAOException e) {
            throw new FinanceTrackerServiceException("Failed to get accounts", e);
        }
        return accounts;
    }

    @Override
    public ArrayList<Transaction> getTransactionsHistory(User user) throws FinanceTrackerServiceException {
        if (user == null) {
            throw new NullUserException();
        }

        if (user.getTransactions().size() != 0) {
            this.user.getTransactions().sort(new TransactionByDateComparator());
        }
        //TODO maybe it should return copies
        //TODO
        //TODO
        return user.getTransactions();
    }

    @Override
    public ArrayList<Transaction> getTransactionsHistory(User user, Date date) throws FinanceTrackerServiceException {
        if (user == null) {
            throw new NullUserException();
        }

        ArrayList<Transaction> transactionsFromSpecificDate = new ArrayList<>();
        //TODO think of the date representation and how to check them
        /**
         for(Transaction transaction:user.getTransactions()){
         if(transaction.getDate().){
         transactionsFromSpecificDate.add(transaction);
         }
         } */
        //TODO maybe it should return copies
        return transactionsFromSpecificDate;
    }

    @Override
    public void transferMoney(User user, Account accountSender, Account accountReceiver, BigDecimal sum) throws FinanceTrackerServiceException {
        if (user == null) {
            throw new NullUserException();
        }

        if (!user.hasAccount(accountSender.getId())) {
            //TODO create message
            throw new UserDoesNotHaveSuchAccountException();
        }

        if (!user.hasAccount(accountReceiver.getId())) {
            //TODO create message
            throw new UserDoesNotHaveSuchAccountException();
        }

        if (accountSender.getId() == accountReceiver.getId()) {
            //TODO create message
            throw new TransferMoneyToTheSameAccountAttemptException();
        }

        if (sum.doubleValue() < 0) {
            //TODO create message
            throw new InvalidTransactionSumException();
        }

        accountSender.setBalance(accountSender.getBalance().subtract(sum));
        accountReceiver.setBalance(accountReceiver.getBalance().add(sum));

        try {
            accountDAO.editAccount(this.user, accountSender);
            accountDAO.editAccount(this.user, accountReceiver);
        } catch (DAOException e) {
            //TODO create message
            throw new FinanceTrackerServiceException(e);
        }
    }

    @Override
    public BigDecimal getCurrentBalance(User user) throws FinanceTrackerServiceException {
        if (user == null) {
            throw new NullUserException();
        }

        BigDecimal currentBalance = new BigDecimal(0.0);
        for (Account account : user.getAccounts()) {
            currentBalance = currentBalance.add(account.getBalance());
        }
        return currentBalance;
    }

    private class TransactionByDateComparator implements Comparator<Transaction> {

        @Override
        public int compare(Transaction transaction1, Transaction transaction2) {
            return transaction1.getDate().compareTo(transaction2.getDate());
        }
    }
}
