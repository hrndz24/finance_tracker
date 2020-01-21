package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.stringvalues.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.math.BigDecimal;

public class AddAccount implements Command {
    @Override
    public String execute(String request) {
        String response;
        String[] requestData = request.split(delimiter);

        int userId = Integer.parseInt(requestData[0]);
        String accountName = requestData[1];
        BigDecimal balance = new BigDecimal(Double.parseDouble(requestData[2]));

        Account account = new Account(accountName, balance);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.addAccount(userId, account);
            response = StringProperty.getStringValue("accountAdded");
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToAddAccount") + e.getMessage();
        }
        return response;
    }
}
