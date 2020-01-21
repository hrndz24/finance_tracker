package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.stringvalues.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.math.BigDecimal;

public class EditAccount implements Command {
    @Override
    public String execute(String request) {
        String response;
        String[] requestData = request.split(delimiter);

        int userId = Integer.parseInt(requestData[0]);
        int accountId = Integer.parseInt(requestData[1]);
        String accountName = requestData[2];
        BigDecimal balance = new BigDecimal(Double.parseDouble(requestData[3]));

        Account account = new Account(accountName, balance, accountId);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.editAccount(userId, account);
            response = StringProperty.getStringValue("accountEdited");
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToEditAccount") + e.getMessage();
        }
        return response;
    }
}
