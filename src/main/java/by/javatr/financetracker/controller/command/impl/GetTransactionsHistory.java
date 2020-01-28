package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.bean.Income;
import by.javatr.financetracker.bean.Transaction;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.stringvalues.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

public class GetTransactionsHistory implements Command {

    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            Transaction[] transactions = financeTracker.getTransactionsHistory(userId);
            StringBuilder stringBuilder = new StringBuilder();
            String sumSign = " ";
            for (Transaction transaction : transactions) {
                if (transaction.getClass() == Expense.class) {
                    sumSign = "-";
                } else if (transaction.getClass() == Income.class) {
                    sumSign = "+";
                }
                String transactionString = sumSign + transaction.getSum().doubleValue() + delimiter
                        + transaction.getCategory() + delimiter + transaction.getAccountId()
                        + delimiter + transaction.getDate().toString().replace(delimiter, "_")
                        + delimiter + transaction.getNote() +delimiter+transaction.getId();
                stringBuilder.append(transactionString).append("\n");
            }
            response = stringBuilder.toString();
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToGetTransactions") + e.getMessage();
        }
        return response;
    }
}