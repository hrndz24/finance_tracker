package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.Income;
import by.javatr.financetracker.dao.exception.IncomeNotFoundException;
import by.javatr.financetracker.dao.exception.UserNotFoundException;

import java.util.ArrayList;

public interface IncomeDAO {

    void addIncome(Income income);

    void editIncome(Income income) throws IncomeNotFoundException;

    void deleteIncome(Income income) throws IncomeNotFoundException;

    ArrayList<Income> getAllIncomes(String userLogIn) throws UserNotFoundException;
}
