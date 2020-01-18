package by.javatr.financetracker.controller;

import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.command.CommandName;
import by.javatr.financetracker.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

final class CommandProvider  {
    private final Map<CommandName, Command> repository = new HashMap<>();

    CommandProvider(){
        repository.put(CommandName.SIGN_UP, new SignUp());
        repository.put(CommandName.LOG_IN, new LogIn());
        repository.put(CommandName.EDIT_LOG_IN, new EditLogIn());
        repository.put(CommandName.CHANGE_PASSWORD, new ChangePassword());
        repository.put(CommandName.DEACTIVATE_USER_ACCOUNT, new DeactivateUserAccount());
        repository.put(CommandName.ADD_EXPENSE, new AddExpense());
        repository.put(CommandName.GET_ACCOUNTS, new GetAccounts());
    }

    Command getCommand(String name){
        Command command = null;
        CommandName commandName = null;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e){
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return  command;
    }
}
