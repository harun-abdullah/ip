import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class Rakan {

    private Ui ui;
    private TaskList taskList;
    private Storage storage;

    public Rakan(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadTasks(), storage);
    }

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
                    taskList.handleMark(userInput, true, ui);
                } else if (userInput.toLowerCase().startsWith("unmark")) {
                    taskList.handleMark(userInput, false, ui);
                } else if (userInput.toLowerCase().startsWith("delete")) {
                    int index = Parser.parseDelete(userInput);
                    Task task = taskList.getTasks().get(index);
                    taskList.handleDelete(index);
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
                    Deadline deadline = new Deadline(description, by);
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
}
