package by.javatr.financetracker.service;

import by.javatr.financetracker.entity.*;
import by.javatr.financetracker.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface FinanceTrackerService {

    void addExpense(int userId, Expense expense) throws ServiceException;

    void editExpense(int userId, Expense editedExpense) throws ServiceException;

    void deleteExpense(int userId, int expenseId) throws ServiceException;

    void addIncome(int userId, Income income) throws ServiceException;

    void editIncome(int userId, Income editedIncome) throws ServiceException;

    void deleteIncome(int userId, int incomeId) throws ServiceException;

    void addAccount(int userId, Account account) throws ServiceException;

    void editAccount(int userId, Account editedAccount) throws ServiceException;

    void deleteAccount(int userId, int accountId) throws ServiceException;

    Account[] getAccounts(int userId) throws ServiceException;

    Transaction[] getTransactionsHistory(int userId) throws ServiceException;

    Transaction[] getTransactionsHistory(int userId, Date date) throws ServiceException;

    void transferMoney(int userId, Account accountSender, Account accountReceiver, BigDecimal sum) throws ServiceException;

    BigDecimal getCurrentBalance(int userId) throws ServiceException;

    Map<ExpenseCategory, BigDecimal> getExpensesByCategory(int userId) throws ServiceException;

    Map<IncomeCategory, BigDecimal> getIncomesByCategory(int userId) throws ServiceException;

}
