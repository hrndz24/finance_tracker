package by.javatr.financetracker.controller;

import by.javatr.financetracker.controller.command.Command;

public final class Controller {

    private final CommandProvider provider = new CommandProvider();

    private final char delimiter = ' ';

    public String executeTask(String request) {
        String commandName;
        Command executionCommand;

        commandName = request.substring(0, request.indexOf(delimiter));
        executionCommand = provider.getCommand(commandName);

        String response;
        response = executionCommand.execute(request.substring(request.indexOf(delimiter) + 1));
        return response;
    }
}
