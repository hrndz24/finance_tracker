package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

public class DeleteExpense implements Command {
    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);
        int expenseId = Integer.parseInt(requestData[1]);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.deleteExpense(userId, expenseId);
            response = StringProperty.getStringValue("expenseDeleted");
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToDeleteExpense") + e.getMessage();
        }
        return response;
    }
}
