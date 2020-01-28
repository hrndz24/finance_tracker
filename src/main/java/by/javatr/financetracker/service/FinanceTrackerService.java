package by.javatr.financetracker.service;

import by.javatr.financetracker.bean.*;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface FinanceTrackerService {

    void addExpense(int userId, Expense expense) throws FinanceTrackerServiceException;

    void editExpense(int userId, Expense editedExpense) throws FinanceTrackerServiceException;

    void deleteExpense(int userId, int expenseId) throws FinanceTrackerServiceException;

    void addIncome(int userId, Income income) throws FinanceTrackerServiceException;

    void editIncome(int userId, Income editedIncome) throws FinanceTrackerServiceException;

    void deleteIncome(int userId, int incomeId) throws FinanceTrackerServiceException;

    void addAccount(int userId, Account account) throws FinanceTrackerServiceException;

    void editAccount(int userId, Account editedAccount) throws FinanceTrackerServiceException;

    void deleteAccount(int userId, int accountId) throws FinanceTrackerServiceException;

    Account[] getAccounts(int userId) throws FinanceTrackerServiceException;

    Transaction[] getTransactionsHistory(int userId) throws FinanceTrackerServiceException;

    Transaction[] getTransactionsHistory(int userId, Date date) throws FinanceTrackerServiceException;

    void transferMoney(int userId, Account accountSender, Account accountReceiver, BigDecimal sum) throws FinanceTrackerServiceException;

    BigDecimal getCurrentBalance(int userId) throws FinanceTrackerServiceException;

    Map<ExpenseCategory, BigDecimal> getExpensesByCategory(int userId) throws FinanceTrackerServiceException;

    Map<IncomeCategory, BigDecimal> getIncomesByCategory(int userId) throws FinanceTrackerServiceException;

}
