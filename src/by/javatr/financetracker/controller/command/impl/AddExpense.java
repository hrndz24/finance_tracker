package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.bean.Expense;
import by.javatr.financetracker.bean.ExpenseCategory;
import by.javatr.financetracker.bean.User;
import by.javatr.financetracker.controller.command.Command;
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

        String logIn = requestData[0];
        int id = Integer.parseInt(requestData[1]);

        BigDecimal sum = new BigDecimal(Double.parseDouble(requestData[2]));
        ExpenseCategory category = ExpenseCategory.valueOf(requestData[3].toUpperCase());
        int accountId = Integer.parseInt(requestData[4]);
        String note;
        if(requestData.length<7) {
            note = "";
        } else{
            note = requestData[6];
        }
        Date date;
        try {
            date = new SimpleDateFormat("EEE_MMM_dd_HH:mm:ss_zzz_yyyy", Locale.ENGLISH).parse(requestData[5]);
        } catch (ParseException e) {
            response = "Failed to add expense because of invalid date representation";
            return response;
        }

        User user = new User(logIn, id);
        Expense expense = new Expense(sum, category, accountId, date, note);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.addExpense(user, expense);
            response = "new expense was added";
        } catch (FinanceTrackerServiceException e) {
            response = "Failed to add expense because of " + e.getMessage();
        }
        return response;
    }
}
