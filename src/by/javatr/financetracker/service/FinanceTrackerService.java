package by.javatr.financetracker.service;

import by.javatr.financetracker.bean.*;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public interface FinanceTrackerService {

    void setUser(User user) throws FinanceTrackerServiceException;

    void addExpense(User user, Expense expense) throws FinanceTrackerServiceException;

    //TODO overload this method with different data
    void editExpense(User user, Expense editedExpense) throws FinanceTrackerServiceException;

    void deleteExpense(User user, Expense expense) throws FinanceTrackerServiceException;

    void addIncome(User user, Income income) throws FinanceTrackerServiceException;

    void editIncome(User user, Income editedIncome) throws FinanceTrackerServiceException;

    void deleteIncome(User user, Income income) throws FinanceTrackerServiceException;

    void addAccount(User user, Account account) throws FinanceTrackerServiceException;

    void editAccount(User user, Account editedAccount) throws FinanceTrackerServiceException;

    void deleteAccount(User user, Account account) throws FinanceTrackerServiceException;

    ArrayList<Account> getAccounts(User user) throws FinanceTrackerServiceException;

    ArrayList<Transaction> getTransactionsHistory(User user) throws FinanceTrackerServiceException;

    ArrayList<Transaction> getTransactionsHistory(User user, Date date) throws FinanceTrackerServiceException;

    void transferMoney(User user, Account accountSender, Account accountReceiver, BigDecimal sum) throws FinanceTrackerServiceException;

    BigDecimal getCurrentBalance(User user) throws FinanceTrackerServiceException;

    /**
     * HashMap<ExpenseCategory, BigDecimal> getExpensesByCategory();
     * HashMap<IncomeCategory, BigDecimal> getIncomesByCategory();
     * */
}
