package by.javatr.financetracker.dao;

import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.dao.exception.*;

public interface UserDAO {

    //TODO maybe replace all those exceptions with UserDAOException
    void addUser(User user, char[] password) throws FailedAddUserException;

    boolean hasUser(String logIn, char[] password) throws FailedFindUserException;

    boolean hasUser(String logIn) throws FailedFindUserException;

    void deleteUser(User user) throws UserNotFoundException, FailedDeleteUserException;

    void editLogIn(String oldLogIn, String newLogIn) throws UserNotFoundException, FailedEditUserInfoException;

    void editPassword(String logIn, char[] newPassword) throws UserNotFoundException, FailedEditUserInfoException;

    //TODO maybe it should have FailedGetUserException
    User getUser(String logIn) throws UserNotFoundException, UserDAOException;
}
