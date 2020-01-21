package by.javatr.financetracker.view;

import by.javatr.financetracker.bean.Account;
import by.javatr.financetracker.controller.Controller;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        String delimiter = " ";
        Controller controller = new Controller();

        String logInResponse = controller.executeTask("log_in nat@nat.com VeryStrongPassword");
        int userId = 0;
        try {
            userId = Integer.parseInt(logInResponse.split(delimiter)[0]);
        } catch (NumberFormatException e) {
            System.out.println(logInResponse);
        }

        String getAccountsResponse = controller.executeTask("get_accounts" + delimiter + userId);
        Account[] accounts = parseAccounts(getAccountsResponse);
        
        // userId sum category accountId date_like_that note_too
        String addExpenseResponse = controller.executeTask("add_expense" + delimiter + userId +
                delimiter + 45.56 + delimiter + ExpenseCategory.ENTERTAINMENT + delimiter + accounts[3].getId() + delimiter
                + new Date().toString().replace(" ", "_"));

        System.out.println(addExpenseResponse);
        

        
        // userId sum category accountId date_like_that note_too
        String addIncomeResponse = controller.executeTask("add_income " + userId +
                " 123.55 " + IncomeCategory.SAVINGS + delimiter + accounts[2].getId() + delimiter +
                new Date().toString().replace(" ", "_")
                + " what_can_be_better_than_finding_money_in_old_jacket");
        System.out.println(addIncomeResponse);
        

        
        //changes accountId to be account's name
        String getTransactionsHistoryResponse = controller.executeTask("get_transactions_history "+userId);
        for (Account account : accounts) {
            getTransactionsHistoryResponse = getTransactionsHistoryResponse.replace(String.valueOf(account.getId()), account.getName());
            getTransactionsHistoryResponse = getTransactionsHistoryResponse.replace("_", " ");
        }
        System.out.println(getTransactionsHistoryResponse);
        */

        
        String changePasswordResponse = controller.executeTask(
                "change_password "+userId+" nat@nat.com VeryStrongPassword AnotherStrongPassword");
        System.out.println(changePasswordResponse);
        

        
        String getExpensesByCategoryResponse = controller.executeTask("get_expenses_by_category "+userId);
        System.out.println(getExpensesByCategoryResponse);
        


    }

    private static Account[] parseAccounts(String accountsToParse) {
        String[] accountsInString = accountsToParse.split("\n");
        Account[] accounts = new Account[accountsInString.length];
        String[] accountInfo;
        for (int i = 0; i < accounts.length; i++) {
            accountInfo = accountsInString[i].split(" ");
            accounts[i] = new Account(accountInfo[1].replace("_", " "), new BigDecimal(Double.parseDouble(accountInfo[2])),
                    Integer.parseInt(accountInfo[0]));
        }
        return accounts;
    }
}
