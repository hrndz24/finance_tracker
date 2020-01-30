package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

public class DeleteIncome implements Command {
    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);
        int incomeId = Integer.parseInt(requestData[1]);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.deleteIncome(userId, incomeId);
            response = StringProperty.getStringValue("incomeDeleted");
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToDeleteIncome") + e.getMessage();
        }
        return response;
    }
}
