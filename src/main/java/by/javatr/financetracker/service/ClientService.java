package by.javatr.financetracker.service;

import by.javatr.financetracker.entity.User;
import by.javatr.financetracker.exception.ServiceException;

public interface ClientService {

    User signUp(String logIn, char[] password) throws ServiceException;

    User logIn(String logIn, char[] password) throws ServiceException;

    void deactivateAccount(User user, char[] password) throws ServiceException;

    void editLogIn(User user, String newLogIn) throws ServiceException;

    void changePassword(User user, char[] oldPassword, char[] newPassword) throws ServiceException;

}
