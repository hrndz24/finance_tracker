package by.javatr.financetracker.controller;

import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.command.CommandName;
import by.javatr.financetracker.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

final class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();

    CommandProvider() {
        repository.put(CommandName.SIGN_UP, new SignUp());
        repository.put(CommandName.LOG_IN, new LogIn());
        repository.put(CommandName.EDIT_LOG_IN, new EditLogIn());
        repository.put(CommandName.CHANGE_PASSWORD, new ChangePassword());
        repository.put(CommandName.DEACTIVATE_USER_ACCOUNT, new DeactivateUserAccount());

        repository.put(CommandName.ADD_EXPENSE, new AddExpense());
        repository.put(CommandName.EDIT_EXPENSE, new EditExpense());
        repository.put(CommandName.DELETE_EXPENSE, new DeleteExpense());

        repository.put(CommandName.ADD_INCOME, new AddIncome());
        repository.put(CommandName.EDIT_INCOME, new EditIncome());
        repository.put(CommandName.DELETE_INCOME, new DeleteIncome());

        repository.put(CommandName.ADD_ACCOUNT, new AddAccount());
        repository.put(CommandName.EDIT_ACCOUNT, new EditAccount());
        repository.put(CommandName.DELETE_ACCOUNT, new DeleteAccount());

        repository.put(CommandName.GET_ACCOUNTS, new GetAccounts());
        repository.put(CommandName.GET_CURRENT_BALANCE, new GetCurrentBalance());

        repository.put(CommandName.GET_TRANSACTIONS_HISTORY, new GetTransactionsHistory());
        repository.put(CommandName.GET_TRANSACTIONS_HISTORY_BY_DATE, new GetTransactionsHistoryByDate());
        repository.put(CommandName.TRANSFER_MONEY, new TransferMoney());

        repository.put(CommandName.GET_EXPENSES_BY_CATEGORY, new GetExpensesByCategory());
        repository.put(CommandName.GET_INCOMES_BY_CATEGORY, new GetIncomesByCategory());

        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    Command getCommand(String name) {
        Command command;
        CommandName commandName;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}
