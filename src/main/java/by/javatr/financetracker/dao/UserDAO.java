package by.javatr.financetracker.dao;

import by.javatr.financetracker.entity.User;
import by.javatr.financetracker.dao.exception.DAOException;

public interface UserDAO {

    void addUser(User user, char[] password) throws DAOException;

    boolean hasUser(String logIn, char[] password) throws DAOException;

    boolean hasUser(String logIn) throws DAOException;

    void deleteUser(int userId) throws DAOException;

    void editLogIn(int userId, String newLogIn) throws DAOException;

    void editPassword(int userId, char[] newPassword) throws DAOException;

    User getUser(String logIn) throws DAOException;
}
