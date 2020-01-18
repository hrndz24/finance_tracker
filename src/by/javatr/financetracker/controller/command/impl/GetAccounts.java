package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.util.ArrayList;

public class GetAccounts implements Command {
    @Override
    public String execute(String request) {
        String response = "";
        String[] requestData = request.split(delimiter);

        String logIn = requestData[0];
        int id = Integer.parseInt(requestData[1]);

        User user = new User(logIn, id);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            ArrayList<Account> accounts = financeTracker.getAccounts(user);
            for (Account account : accounts) {
                response += account.getId()+ " ";
            }
        } catch (FinanceTrackerServiceException e) {
            response = "Failed to get accounts because of ";
        }
        return response;
    }
}
