package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.bean.ExpenseCategory;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.stringvalues.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExpense implements Command {

    @Override
    public String execute(String request) {
        String response;
        String[] requestData = request.split(delimiter);

        int userId = Integer.parseInt(requestData[0]);

        BigDecimal sum = new BigDecimal(Double.parseDouble(requestData[1]));
        ExpenseCategory category = ExpenseCategory.valueOf(requestData[2].toUpperCase());
        int accountId = Integer.parseInt(requestData[3]);
        String note;
        if (requestData.length < 6) {
            note = "";
        } else {
            note = requestData[5];
        }
        Date date;
        try {
            date = new SimpleDateFormat(StringProperty.getStringValue("dateFormat"), Locale.ENGLISH).parse(requestData[4]);
        } catch (ParseException e) {
            response = StringProperty.getStringValue("failedBecauseOfDate");
            return response;
        }

        Expense expense = new Expense(sum, category, accountId, date, note);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.addExpense(userId, expense);
            response = StringProperty.getStringValue("expenseAdded");
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToAddExpense") + e.getMessage();
        }
        return response;
    }
}
