package rakan;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import rakan.parser.ParsedDeadline;
import rakan.parser.ParsedEvent;
import rakan.parser.ParsedMark;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.task.DeadLine;
import rakan.task.Event;
import rakan.task.Task;
import rakan.task.ToDo;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class Rakan {

    private Ui ui;
    private TaskList taskList;
    private Storage storage;

    /**
     * Constructs Rakan instance.
     *
     * @param filePath File path for storage.
     */
    public Rakan(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadTasks());
    }

    /**
     * Returns a string response to be displayed in the GUI.
     * Also forms the main logic for Rakan.
     *
     * @param userInput User input String for Rakan to process.
     * @return String response.
     */
    public String getResponse(String userInput) {
        try {
            if (userInput.equalsIgnoreCase("bye")) {
                return ui.exit();
            } else if (userInput.equalsIgnoreCase("list")) {
                return ui.showList(taskList.getTasks());
            } else if (userInput.toLowerCase().startsWith("mark")) {
                ParsedMark parsedMark = Parser.parseMark(userInput, true, taskList.getTasks().size());
                Task task = taskList.getTasks().get(parsedMark.getTaskIndex());
                taskList.handleMark(parsedMark);
                storage.saveTasks(taskList.getTasks());
                return "Nice! I've marked this task as done:\n" + task;
            } else if (userInput.toLowerCase().startsWith("unmark")) {
                ParsedMark parsedMark = Parser.parseMark(userInput, false, taskList.getTasks().size());
                Task task = taskList.getTasks().get(parsedMark.getTaskIndex());
                taskList.handleMark(parsedMark);
                storage.saveTasks(taskList.getTasks());
                return "OK, I've marked this task as not done yet:\n" + task;
            } else if (userInput.toLowerCase().startsWith("delete")) {
                int index = Parser.parseDelete(userInput);
                Task task = taskList.getTasks().get(index);
                taskList.handleDelete(index);
                storage.saveTasks(taskList.getTasks());
                return "Yes boss. I've removed the task below:\n" + task +
                        "\nNow you have " + taskList.getTasks().size() + " tasks in the list";
            } else if (userInput.toLowerCase().startsWith("todo")) {
                String description = Parser.parseTodo(userInput);
                ToDo toDo = new ToDo(description);
                taskList.addTask(toDo);
                storage.saveTasks(taskList.getTasks());
                return "Got it. I've added this task:\n  " + toDo +
                        "\nNow you have " + taskList.getTasks().size() + " tasks.";
            } else if (userInput.toLowerCase().startsWith("deadline")) {
                ParsedDeadline parsedDeadline = Parser.parseDeadline(userInput);
                DeadLine deadline = new DeadLine(parsedDeadline.getDescription(), parsedDeadline.getBy());
                taskList.addTask(deadline);
                storage.saveTasks(taskList.getTasks());
                return "Got it. I've added this task:\n  " + deadline +
                        "\nNow you have " + taskList.getTasks().size() + " tasks.";
            } else if (userInput.toLowerCase().startsWith("event")) {
                ParsedEvent parsedEvent = Parser.parseEvent(userInput);
                Event event = new Event(parsedEvent.getDescription(), parsedEvent.getFrom(), parsedEvent.getTo());
                taskList.addTask(event);
                storage.saveTasks(taskList.getTasks());
                return "Got it. I've added this task:\n  " + event +
                        "\nNow you have " + taskList.getTasks().size() + " tasks.";
            } else if (userInput.toLowerCase().startsWith("find")) {
                String keyword = Parser.parseFind(userInput);
                ArrayList<Task> results = taskList.find(keyword);
                return ui.showFindResults(results);
            } else {
                throw new RakanException("Sorry, not sure what that means");
            }
        } catch (RakanException | IOException e) {
            return e.getMessage();
        }
    }

    public Ui getUi() {
        return ui;
    }

}
