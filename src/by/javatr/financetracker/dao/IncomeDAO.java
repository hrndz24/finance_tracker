package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.Income;
import by.javatr.financetracker.dao.exception.DAOException;

public interface IncomeDAO {

    void addIncome(int userId, Income income) throws DAOException;

    void editIncome(int userId, Income income) throws DAOException;

    void deleteIncome(int userId, int incomeId) throws DAOException;

    Income getIncome(int userId, int incomeId) throws DAOException;

    Income[] getAllIncomes(int userId) throws DAOException;
}
