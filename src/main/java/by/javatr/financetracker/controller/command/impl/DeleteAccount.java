package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.exception.ServiceException;
import by.javatr.financetracker.factory.ServiceFactory;

public class DeleteAccount implements Command {
    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);
        int accountId = Integer.parseInt(requestData[1]);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.deleteAccount(userId, accountId);
            response = StringProperty.getStringValue("accountDeleted");
        } catch (ServiceException e) {
            response = StringProperty.getStringValue("failedToDeleteAccount") + e.getMessage();
        }
        return response;
    }
}
