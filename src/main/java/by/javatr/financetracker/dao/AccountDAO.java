package by.javatr.financetracker.dao;

import by.javatr.financetracker.entity.Account;
import by.javatr.financetracker.dao.exception.DAOException;

public interface AccountDAO {

    void addAccount(int userId, Account account) throws DAOException;

    void editAccount(int userId, Account editedAccount) throws DAOException;

    void deleteAccount(int userId, int accountId) throws DAOException;

    Account[] getAllAccounts(int userId) throws DAOException;

    Account getAccount(int userId, int accountId) throws DAOException;

    boolean hasAccount(int userId, int accountId) throws DAOException;
}
