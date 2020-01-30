package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.entity.User;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.ClientService;
import by.javatr.financetracker.service.exception.ClientServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

public class ChangePassword implements Command {

    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);
        String logIn = requestData[1];
        char[] oldPassword = requestData[2].toCharArray();
        char[] newPassword = requestData[3].toCharArray();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();

        try {
            clientService.changePassword(new User(logIn, userId), oldPassword, newPassword);
            response = StringProperty.getStringValue("passwordChanged");
        } catch (ClientServiceException e) {
            response = StringProperty.getStringValue("failedToChangePassword") + e.getMessage();
        }
        return response;
    }
}