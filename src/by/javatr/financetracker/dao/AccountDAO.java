package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.exception.*;

import java.util.ArrayList;

public interface AccountDAO {

    //TODO think of having the common exception AccountDAOException
    void addAccount(User user, Account account) throws DAOException;

    void editAccount(User user, Account account) throws DAOException;

    void deleteAccount(User user, int accountId) throws DAOException;

    ArrayList<Account> getAllAccounts(User user) throws DAOException;

    boolean hasAccount(User user, int accountId) throws DAOException;
}
