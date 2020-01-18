package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.Income;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.exception.DAOException;

import java.util.ArrayList;

public interface IncomeDAO {

    void addIncome(User user, Income income) throws DAOException;

    void editIncome(User user, Income income) throws DAOException;

    void deleteIncome(User user, Income income) throws DAOException;

    ArrayList<Income> getAllIncomes(User user) throws DAOException;
}
