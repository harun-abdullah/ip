package Rakan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static String parseTodo(String input) throws RakanException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new RakanException("Hold on. The description of a todo cannot be empty.");
        }
        return description;
    }

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

    public static int parseDelete(String input) throws RakanException {
        String[] parts = input.split("\\s+", 2);

        if (parts.length < 2) {
            throw new RakanException("Please provide a task number to delete.");
        }

        int taskNumber = Integer.parseInt(parts[1]);
        return taskNumber - 1;
    }

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

}

