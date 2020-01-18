package by.javatr.financetracker.controller.command;

public interface Command {
    String delimiter = " ";
    String execute(String request);
}
