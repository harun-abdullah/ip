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
     * Runs the main logic of the program.
     * The logic is split depending on the user input.
     */
    public void run() {
        ui.greet();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();

            try {
                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                } else if (userInput.equalsIgnoreCase("list")) {
                    ui.showList(taskList.getTasks());
                } else if (userInput.toLowerCase().startsWith("mark")) {
                    ParsedMark parsedMark = Parser.parseMark(userInput, true, taskList.getTasks().size());
                    Task task = taskList.getTasks().get(parsedMark.getTaskIndex());
                    taskList.handleMark(parsedMark);
                    storage.saveTasks(taskList.getTasks());
                    ui.entry("Nice! I've marked this task as done:"
                            + "\n" + task);
                } else if (userInput.toLowerCase().startsWith("unmark")) {
                    ParsedMark parsedMark = Parser.parseMark(userInput, false, taskList.getTasks().size());
                    Task task = taskList.getTasks().get(parsedMark.getTaskIndex());
                    taskList.handleMark(parsedMark);
                    storage.saveTasks(taskList.getTasks());
                    ui.entry("OK, I've marked this task as not done yet:"
                            + "\n" + task);
                } else if (userInput.toLowerCase().startsWith("delete")) {
                    int index = Parser.parseDelete(userInput);
                    Task task = taskList.getTasks().get(index);
                    taskList.handleDelete(index);
                    storage.saveTasks(taskList.getTasks());
                    ui.entry("Yes boss. I've removed the task below:\n" +
                            task + "\n"
                            + "Now you have " + taskList.getTasks().size() + " tasks in the list");
                } else if (userInput.toLowerCase().startsWith("todo")) {
                    String description = Parser.parseTodo(userInput);
                    ToDo toDo = new ToDo(description);
                    taskList.addTask(toDo);
                    storage.saveTasks(taskList.getTasks());
                    ui.entry("Got it. I've added this task:\n  " + toDo
                            + "\nNow you have " + taskList.getTasks().size() + " tasks in the list.");
                } else if (userInput.toLowerCase().startsWith("deadline")) {
                    ParsedDeadline parsedDeadline = Parser.parseDeadline(userInput);
                    String description = parsedDeadline.getDescription();
                    LocalDateTime by = parsedDeadline.getBy();
                    DeadLine deadline = new DeadLine(description, by);
                    taskList.addTask(deadline);
                    storage.saveTasks(taskList.getTasks());
                    ui.entry("Got it. I've added this task:\n  "
                            + deadline + "\n"
                            + "Now you have " + taskList.getTasks().size() + " tasks in the list.");
                } else if (userInput.toLowerCase().startsWith("event")) {
                    ParsedEvent parsedEvent = Parser.parseEvent(userInput);
                    String description = parsedEvent.getDescription();
                    LocalDateTime from = parsedEvent.getFrom();
                    LocalDateTime to = parsedEvent.getTo();

                    Event event = new Event(description, from, to);
                    taskList.addTask(event);
                    storage.saveTasks(taskList.getTasks());
                    ui.entry("Got it. I've added this task:\n  " + event
                            + "\nNow you have " + taskList.getTasks().size() + " tasks in the list.");

                } else if (userInput.toLowerCase().startsWith("find")) {
                    String keyword = Parser.parseFind(userInput);
                    ArrayList<Task> results = taskList.find(keyword);
                    ui.showFindResults(results);
                } else {
                    throw new RakanException("Sorry, not sure what that means");
                }
            } catch (RakanException | IOException e) {
                ui.entry(e.getMessage());
            }
        }
        ui.exit();
    }

    public static void main(String[] args) {
        new Rakan("./data/rakan.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Rakan heard: " + input;
    }
}
