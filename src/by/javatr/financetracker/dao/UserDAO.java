package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.exception.*;

public interface UserDAO {

    //TODO maybe replace all those exceptions with UserDAOException
    void addUser(User user, char[] password) throws DAOException;

    boolean hasUser(String logIn, char[] password) throws DAOException;

    boolean hasUser(String logIn) throws DAOException;

    void deleteUser(User user) throws DAOException;

    void editLogIn(User user, String newLogIn) throws DAOException;

    void editPassword(User user, char[] newPassword) throws DAOException;

    //TODO maybe it should have FailedGetUserException
    User getUser(String logIn) throws DAOException;
}
