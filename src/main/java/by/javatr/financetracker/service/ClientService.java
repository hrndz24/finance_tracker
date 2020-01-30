package by.javatr.financetracker.service;

import by.javatr.financetracker.entity.User;
import by.javatr.financetracker.service.exception.ClientServiceException;

public interface ClientService {

    User signUp(String logIn, char[] password) throws ClientServiceException;

    User logIn(String logIn, char[] password) throws ClientServiceException;

    void deactivateAccount(User user, char[] password) throws ClientServiceException;

    void editLogIn(User user, String newLogIn) throws ClientServiceException;

    void changePassword(User user, char[] oldPassword, char[] newPassword) throws ClientServiceException;

}
