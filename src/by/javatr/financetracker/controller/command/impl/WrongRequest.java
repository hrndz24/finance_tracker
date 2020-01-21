package by.javatr.financetracker.controller.command.impl;

import by.javatr.financetracker.controller.command.Command;
import by.javatr.financetracker.controller.stringvalues.StringProperty;

public class WrongRequest implements Command {
    @Override
    public String execute(String request) {
        return StringProperty.getStringValue("wrongRequestResponse");
    }
}
