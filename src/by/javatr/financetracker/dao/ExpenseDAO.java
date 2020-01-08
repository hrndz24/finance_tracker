package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.exception.ExpenseNotFoundException;
import by.javatr.financetracker.dao.exception.UserNotFoundException;

import java.util.ArrayList;

public interface ExpenseDAO {

    void addExpense(User user, Expense expense);

    void editExpense(User user, Expense expense) throws ExpenseNotFoundException;

    void deleteExpense(User user, Expense expense) throws ExpenseNotFoundException;

    ArrayList<Expense> getAllExpenses(User user) throws UserNotFoundException;
}
