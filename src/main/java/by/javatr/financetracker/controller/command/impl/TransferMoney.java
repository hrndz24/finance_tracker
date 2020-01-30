package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.entity.Account;
import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.constants.StringProperty;
import by.javatr.financetracker.service.FinanceTrackerService;
import by.javatr.financetracker.service.exception.FinanceTrackerServiceException;
import by.javatr.financetracker.service.factory.ServiceFactory;

import java.math.BigDecimal;

public class TransferMoney implements Command {
    @Override
    public String execute(String request) {
        String response = "";

        String[] requestData = request.split(delimiter);
        int userId = Integer.parseInt(requestData[0]);

        int accountSenderId = Integer.parseInt(requestData[1]);
        String accountSenderName = requestData[2];
        BigDecimal accountSenderBalance = new BigDecimal(Double.parseDouble(requestData[3]));
        Account accountSender = new Account(accountSenderName, accountSenderBalance, accountSenderId);

        int accountReceiverId = Integer.parseInt(requestData[4]);
        String accountReceiverName = requestData[5];
        BigDecimal accountReceiverBalance = new BigDecimal(Double.parseDouble(requestData[6]));
        Account accountReceiver = new Account(accountReceiverName, accountReceiverBalance, accountReceiverId);

        BigDecimal sum = new BigDecimal(Double.parseDouble(requestData[7]));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        FinanceTrackerService financeTracker = serviceFactory.getFinanceTrackerService();

        try {
            financeTracker.transferMoney(userId, accountSender, accountReceiver, sum);
            response = StringProperty.getStringValue("moneyTransferred");
        } catch (FinanceTrackerServiceException e) {
            response = StringProperty.getStringValue("failedToTransferMoney") + e.getMessage();
        }
        return response;
    }
}
