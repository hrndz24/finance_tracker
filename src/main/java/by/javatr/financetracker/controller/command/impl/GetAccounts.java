package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.entity.Account;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.exception.ServiceException;
import by.javatr.financetracker.factory.ServiceFactory;

public class GetAccounts implements Command {
    @Override
    public String execute(String request) {
        String response = "";
        String[] requestData = request.split(delimiter);

        int id = Integer.parseInt(requestData[0]);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            Account[] accounts = financeTracker.getAccounts(id);
            StringBuilder stringBuilder = new StringBuilder();
            for (Account account : accounts) {
                stringBuilder.append(account.getId()).append(delimiter).append(account.getName()).append(delimiter).
                        append(account.getBalance()).append("\n");
            }
            response = stringBuilder.toString();
        } catch (ServiceException e) {
            response = StringProperty.getStringValue("failedToGetAccounts") + e.getMessage();
        }
        return response;
    }
}
