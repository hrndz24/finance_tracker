package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.entity.ExpenseCategory;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.math.BigDecimal;
import java.util.Map;

public class GetExpensesByCategory implements Command {
    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            Map<ExpenseCategory, BigDecimal> expenseByCategory = financeTracker.getExpensesByCategory(userId);
            for (Map.Entry<ExpenseCategory, BigDecimal> entry : expenseByCategory.entrySet()) {
                stringBuilder.append(entry.getKey()).append(" : ").append(entry.getValue().doubleValue()).append("\n");
            }
            response = stringBuilder.toString();
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToGetExpensesByCategory") + e.getMessage();
        }
        return response;
    }
}
