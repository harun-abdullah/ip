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
            throw new RakanException("OOPS!!! Deadline format should be: deadline <description> /by <time>");
        }

        String[] parts = input.split(" /by ");
        if (parts.length != 2 || parts[1].trim().isEmpty()) {
            throw new RakanException("OOPS!!! The deadline time cannot be empty.");
        }
    }

    private static void validateEventInput(String input) throws RakanException {
        String trimmed = input.trim().toLowerCase();
        if (trimmed.equals("event") || trimmed.matches("event\\s*")) {
            throw new RakanException("OOPS!!! The description of an event cannot be empty.");
        }

        if (!input.toLowerCase().contains(" /from ") || !input.toLowerCase().contains(" /to ")) {
            throw new RakanException("OOPS!!! Event format should be: event <description> /from <start> /to <end>");
        }

        String[] fromSplit = input.split(" /from ");
        if (fromSplit.length != 2) {
            throw new RakanException("OOPS!!! Event format should be: event <description> /from <start> /to <end>");
        }

        String[] toSplit = fromSplit[1].split(" /to ");
        if (toSplit.length != 2 || toSplit[0].trim().isEmpty() || toSplit[1].trim().isEmpty()) {
            throw new RakanException("OOPS!!! Event times cannot be empty.");
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
            throw new RakanException("Woii, Give me something to search for.");
        }
    }

    public static LocalDateTime formatStringToDate(String input) throws RakanException {
        try {
            return LocalDateTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new RakanException("I don't understand that date format. Try d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
        }
    }
    /**
     * Returns description to construct ToDo task.
     *
     * @param input Input from user to create ToDo.
     * @return Description to construct ToDo task.
     * @throws RakanException If input is empty.
     */
    public static String parseTodo(String input) throws RakanException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new RakanException("Hold on. The description of a todo cannot be empty.");
        }
        return description;
    }

    /**
     * Returns ParsedDeadline to construct Deadline task.
     * ParsedDeadline contains description and /by date.
     *
     * @param input Input from user to create Deadline.
     * @return ParsedDeadline object.
     * @throws RakanException If input does not contain valid description or /by date.
     */
    public static ParsedDeadline parseDeadline(String input) throws RakanException {
        String[] parts = input.substring(8).split("/by", 2);
        if (parts.length < 2) {
            throw new RakanException("Wait wait wait. The deadline command needs a description and a /by date.");
        }

        String description = parts[0].trim();
        String byString = parts[1].trim();

        try {
            LocalDateTime byDateTime = LocalDateTime.parse(byString, formatter);
            return new ParsedDeadline(description, byDateTime);
        } catch (DateTimeParseException e) {
            throw new RakanException("I don't understand that date format. Try d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
        }
    }

    /**
     * Returns ParsedEvent to construct Event task.
     * ParsedEvent contains description, /from and /to dates.
     *
     * @param input Input from user to create Event.
     * @return ParsedEvent object.
     * @throws RakanException If input does not contain valid dates.
     */
    public static ParsedEvent parseEvent(String input) throws RakanException {
        String[] parts = input.substring(5).split("/from", 2);
        if (parts.length < 2) {
            throw new RakanException("Hold your horses. The event command needs a description, /from and /to.");
        }

        String description = parts[0].trim();

        String[] timeParts = parts[1].split("/to", 2);
        if (timeParts.length < 2) {
            throw new RakanException("Hold your horses. The event command needs a description, /from and /to.");
        }

        String fromString = timeParts[0].trim();
        String toString = timeParts[1].trim();

        try {
            LocalDateTime from = LocalDateTime.parse(fromString, formatter);
            LocalDateTime to = LocalDateTime.parse(toString, formatter);
            return new ParsedEvent(description, from, to);
        } catch (DateTimeParseException e) {
            throw new RakanException(
                    "I don't understand that date format. Try d/M/yyyy HHmm (e.g., 2/12/2019 1800)."
            );
        }
    }

    /**
     * Returns integer task index for deletion.
     *
     * @param input Input from user to delete task.
     * @return Integer index of task to delete.
     * @throws RakanException If task number is not provided.
     */
    public static int parseDelete(String input) throws RakanException {
        String[] parts = input.split("\\s+", 2);

        if (parts.length < 2) {
            throw new RakanException("Please provide a task number to delete.");
        }

        int taskNumber = Integer.parseInt(parts[1]);
        return taskNumber - 1;
    }

    /**
     * Return ParsedMark object to mark/unmark tasks.
     *
     * @param input Input from user to mark/unmark tasks.
     * @param isMark Boolean to decide to mark/unmark task.
     * @param taskListSize Size of current tasklist.
     * @return ParsedMark object.
     * @throws RakanException If task number is invalid or absent.
     */
    public static ParsedMark parseMark(String input, boolean isMark, int taskListSize) throws RakanException {
        String keyword = isMark ? "mark" : "unmark";
        String[] parts = input.split("\\s+", 2);

        if (parts.length < 2) {
            throw new RakanException("Please provide a task number after \"" + keyword + "\".");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            int index = taskNumber - 1;

            if (index < 0 || index >= taskListSize) {
                throw new RakanException("No such task: " + taskNumber);
            }

            return new ParsedMark(index, isMark);
        } catch (NumberFormatException e) {
            throw new RakanException("Invalid task number: " + parts[1]);
        }
    }

    /**
     * Returns a string to be used in find() in TaskList.
     *
     * @param input Input from user.
     * @return String to be used as keyword.
     * @throws RakanException If no keyword is given.
     */
    public static String parseFind(String input) throws RakanException {
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new RakanException("You gotta give me something to find, man.");
        }
        return keyword;
    }

}

