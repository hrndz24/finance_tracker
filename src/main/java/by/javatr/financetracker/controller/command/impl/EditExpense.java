package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.entity.Expense;
import by.javatr.financetracker.entity.ExpenseCategory;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.exception.ServiceException;
import by.javatr.financetracker.factory.ServiceFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditExpense implements Command {
    @Override
    public String execute(String request) {
        String response;
        String[] requestData = request.split(delimiter);

        int userId = Integer.parseInt(requestData[0]);

        int expenseId = Integer.parseInt(requestData[1]);
        BigDecimal sum = new BigDecimal(Double.parseDouble(requestData[2]));
        ExpenseCategory category = ExpenseCategory.valueOf(requestData[3].toUpperCase());
        int accountId = Integer.parseInt(requestData[4]);
        String note;
        if (requestData.length < 7) {
            note = "";
        } else {
            note = requestData[6];
        }
        Date date;
        try {
            date = new SimpleDateFormat(StringProperty.getStringValue("dateFormat"), Locale.ENGLISH).parse(requestData[5]);
        } catch (ParseException e) {
            response = StringProperty.getStringValue("failedBecauseOfDate");
            return response;
        }

        Expense expense = new Expense(sum, category, accountId, date, note, expenseId);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.editExpense(userId, expense);
            response = StringProperty.getStringValue("expenseEdited");
        } catch (ServiceException e) {
            response = StringProperty.getStringValue("failedToEditExpense") + e.getMessage();
        }
        return response;
    }
}
