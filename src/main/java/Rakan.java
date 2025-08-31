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

    public Rakan() {
        ui = new Ui();
        taskList = new TaskList(Storage.loadTasks());
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
                    taskList.handleDelete(userInput, ui);
                } else if (userInput.toLowerCase().startsWith("todo")) {
                    taskList.createTodo(userInput, ui);
                } else if (userInput.toLowerCase().startsWith("deadline")) {
                    taskList.createDeadline(userInput, ui);
                } else if (userInput.toLowerCase().startsWith("event")) {
                    taskList.createEvent(userInput, ui);
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
        new Rakan().run();
    }
}
