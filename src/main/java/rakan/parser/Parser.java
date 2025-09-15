package rakan.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import rakan.Rakan;
import rakan.RakanException;
import rakan.command.Command;
import rakan.command.DeadlineCommand;
import rakan.command.DeleteCommand;
import rakan.command.ExitCommand;
import rakan.command.FindCommand;
import rakan.command.ListCommand;
import rakan.command.MarkCommand;
import rakan.command.UnmarkCommand;
import rakan.command.TodoCommand;
import rakan.command.EventCommand;


/**
 * Parses and validates the user input.
 */
public class Parser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static Command parse(String input) throws RakanException {
        if (input == null || input.trim().isEmpty()) {
            throw new RakanException("Did u fr send an empty message");
        }

        // get the first word in the input message for the command keyword
        String command = input.toLowerCase().trim().split(" ")[0];
        command = checkCommandAlias(command);

        switch (command) {
        case "todo":
            validateTodoInput(input);
            return new TodoCommand(input);
        case "deadline":
            validateDeadlineInput(input);
            return new DeadlineCommand(input);
        case "event":
            validateEventInput(input);
            return new EventCommand(input);
        case "list":
            return new ListCommand();
        case "mark":
            return new MarkCommand(input);
        case "unmark":
            return new UnmarkCommand(input);
        case "delete":
            return new DeleteCommand(input);
        case "find":
            validateFindInput(input);
            return new FindCommand(input);
        case "bye":
            return new ExitCommand();
        default:
            throw new RakanException("Please type in a valid command vro");
        }
    }

    private static void validateTodoInput(String input) throws RakanException {
        String trimmed = input.trim().toLowerCase();
        if (trimmed.equals("todo") || trimmed.matches("todo\\s*")) {
            throw new RakanException("Bro really forgot the description of his todo task.");
        }
    }

    private static void validateDeadlineInput(String input) throws RakanException {
        String trimmed = input.trim().toLowerCase();
        if (trimmed.equals("deadline") || trimmed.matches("deadline\\s*")) {
            throw new RakanException("Oi, oi, oi. Baaaka. The description of a deadline cannot be empty.");
        }

        if (!input.toLowerCase().contains(" /by ")) {
            throw new RakanException("Alahai, Deadline format should be: deadline <description> /by <time>");
        }

        String[] parts = input.split(" /by ");
        if (parts.length != 2 || parts[1].trim().isEmpty()) {
            throw new RakanException("Alamak, the deadline time cannot be empty.");
        }
    }

    private static void validateEventInput(String input) throws RakanException {
        String trimmed = input.trim().toLowerCase();
        if (trimmed.equals("event") || trimmed.matches("event\\s*")) {
            throw new RakanException("Excuszes moi, the description of an event cannot be empty.");
        }

        if (!input.toLowerCase().contains(" /from ") || !input.toLowerCase().contains(" /to ")) {
            throw new RakanException("Heyyo, Event format should be: event <description> /from <start> /to <end>");
        }

        String[] fromSplit = input.split(" /from ");
        if (fromSplit.length != 2) {
            throw new RakanException("Hello hello, Event format should be: event <description> /from <start> /to <end>");
        }

        String[] toSplit = fromSplit[1].split(" /to ");
        if (toSplit.length != 2 || toSplit[0].trim().isEmpty() || toSplit[1].trim().isEmpty()) {
            throw new RakanException("Oi, Event times cannot be empty.");
        }
    }

    public static Integer validateTaskNumber(String input, int maxTasks) throws RakanException {
        try {
            int taskNum = Integer.parseInt(input);
            if (taskNum < 1 || taskNum > maxTasks) {
                throw new RakanException("Breh. Task number " + taskNum
                        + " is out of range. You have " + maxTasks + " tasks.");
            }
            return taskNum;
        } catch (NumberFormatException e) {
            throw new RakanException("GIVE ME A VALID INTEGER.");
        }
    }

    private static void validateFindInput(String input) throws RakanException {
        String trimmed = input.trim().toLowerCase();
        if (trimmed.equals("find") || trimmed.matches("find\\s*")) {
            throw new RakanException("Got to give me something to find, man.");
        }
    }

    public static LocalDateTime formatStringToDate(String input) throws RakanException {
        try {
            return LocalDateTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new RakanException("I don't understand that date format. Try d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
        }
    }

    private static String checkCommandAlias(String input) {
        switch (input) {
        case "t":
            return "todo";
        case "d":
            return "deadline";
        case "e":
            return "event";
        case "l":
            return "list";
        case "m":
            return "mark";
        case "u":
            return "unmark";
        case "del":
            return "delete";
        case "f":
            return "find";
        case "b":
            return "bye";
        default:
            return input;
        }
    }
}

