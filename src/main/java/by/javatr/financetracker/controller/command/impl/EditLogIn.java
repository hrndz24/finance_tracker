package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.stringvalues.StringProperty;
import by.javatr.financetracker.service.ClientService;
import by.javatr.financetracker.service.exception.ClientServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

public class EditLogIn implements Command {

    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);
        String oldLogIn = requestData[1];
        User user = new User(oldLogIn, userId);

        String newLogIn = requestData[2];

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();

        try {
            clientService.editLogIn(user, newLogIn);
            response = StringProperty.getStringValue("logInEdited");
        } catch (ClientServiceException e) {
            response = StringProperty.getStringValue("failedToEditLogIn") + e.getMessage();
        }
        return response;
    }
}