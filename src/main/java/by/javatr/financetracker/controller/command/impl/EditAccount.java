package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.entity.Account;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.exception.ServiceException;
import by.javatr.financetracker.factory.ServiceFactory;

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
        } catch (ServiceException e) {
            response = StringProperty.getStringValue("failedToEditAccount") + e.getMessage();
        }
        return response;
    }
}
