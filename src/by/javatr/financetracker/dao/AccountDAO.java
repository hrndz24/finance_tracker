package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.exception.*;

import java.util.ArrayList;

public interface AccountDAO {

    //TODO think of having the common exception AccountDAOException
    void addAccount(User user, Account account) throws FailedAddAccountException;

    void editAccount(User user, Account account) throws FailedEditAccountException, AccountNotFoundException;

    void deleteAccount(User user, Account account) throws AccountNotFoundException, FailedDeleteAccountException;

    ArrayList<Account> getAllAccounts(User user) throws UserNotFoundException, AccountDAOException;
}
