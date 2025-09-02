package rakan.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import rakan.RakanException;


/**
 * Parses and validates the user input.
 */
public class Parser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

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

