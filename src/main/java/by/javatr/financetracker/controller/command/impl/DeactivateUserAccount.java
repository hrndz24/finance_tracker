package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.entity.User;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.ClientService;
import by.javatr.financetracker.service.exception.ClientServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

public class DeactivateUserAccount implements Command {

    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);
        String logIn = requestData[1];
        char[] password = requestData[2].toCharArray();
        User user = new User(logIn, userId);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();

        try {
            clientService.deactivateAccount(user, password);
            response = StringProperty.getStringValue("userAccountDeactivated");
        } catch (ClientServiceException e) {
            response = StringProperty.getStringValue("failedToDeactivateAccount") + e.getMessage();
        }
        return response;
    }
}