package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.Transaction;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.stringvalues.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetTransactionsHistoryByDate implements Command {
    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        Date date;
        try {
            date = new SimpleDateFormat(StringProperty.getStringValue("dateFormat"), Locale.ENGLISH).parse(requestData[1]);
        } catch (ParseException e) {
            response = StringProperty.getStringValue("failedBecauseOfDate");
            return response;
        }

        try {
            Transaction[] transactions = financeTracker.getTransactionsHistory(userId, date);
            StringBuilder stringBuilder = new StringBuilder();
            for (Transaction transaction : transactions) {
                String transactionString = transaction.toString();
                stringBuilder.append(transactionString.substring(transactionString.indexOf("@") + 1)).append("\n");
            }
            response = stringBuilder.toString();
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToGetTransactionsByDate");
        }
        return response;
    }
}
