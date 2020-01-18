package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.service.ClientService;
import by.javatr.financetracker.service.exception.ClientServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

public class LogIn implements Command {

    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        String logIn = requestData[0];
        char[] password = requestData[1].toCharArray();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();

        try {
            User user = clientService.logIn(logIn, password);
            response = user.getLogIn() + delimiter + user.getId();
        } catch (ClientServiceException e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }
}