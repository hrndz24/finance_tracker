package by.javatr.financetracker.dao;

import by.javatr.financetracker.entity.Expense;
import by.javatr.financetracker.exception.DAOException;

public interface ExpenseDAO {

    void addExpense(int userId, Expense expense) throws DAOException;

    void editExpense(int userId, Expense editedExpense) throws DAOException;

    void deleteExpense(int userId, int expenseId) throws DAOException;

    Expense getExpense(int userId, int expenseId) throws DAOException;

    Expense[] getAllExpenses(int userId) throws DAOException;
}
