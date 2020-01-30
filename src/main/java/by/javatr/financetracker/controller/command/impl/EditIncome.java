package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.entity.Income;
import by.javatr.financetracker.entity.IncomeCategory;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditIncome implements Command {
    @Override
    public String execute(String request) {
        String response;
        String[] requestData = request.split(delimiter);

        int userId = Integer.parseInt(requestData[0]);

        int incomeId = Integer.parseInt(requestData[1]);
        BigDecimal sum = new BigDecimal(Double.parseDouble(requestData[2]));
        IncomeCategory category = IncomeCategory.valueOf(requestData[3].toUpperCase());
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

        Income income = new Income(sum, category, accountId, date, note, incomeId);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.editIncome(userId, income);
            response = StringProperty.getStringValue("incomeEdited");
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToEditIncome") + e.getMessage();
        }
        return response;
    }
}
