package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.exception.DAOException;

import java.util.ArrayList;

public interface ExpenseDAO {

    void addExpense(User user, Expense expense) throws DAOException;

    void editExpense(User user, Expense expense) throws DAOException;

    void deleteExpense(User user, Expense expense) throws DAOException;

    ArrayList<Expense> getAllExpenses(User user) throws DAOException;
}
