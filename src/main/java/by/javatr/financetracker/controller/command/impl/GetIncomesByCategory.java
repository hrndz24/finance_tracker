package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.IncomeCategory;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.stringvalues.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.math.BigDecimal;
import java.util.Map;

public class GetIncomesByCategory implements Command {
    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            Map<IncomeCategory, BigDecimal> expenseByCategory = financeTracker.getIncomesByCategory(userId);
            for (Map.Entry<IncomeCategory, BigDecimal> entry : expenseByCategory.entrySet()) {
                stringBuilder.append(entry.getKey()).append(" : ").append(entry.getValue().doubleValue()).append("\n");
            }
            response = stringBuilder.toString();
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToGetIncomesByCategory") + e.getMessage();
        }
        return response;
    }
}
