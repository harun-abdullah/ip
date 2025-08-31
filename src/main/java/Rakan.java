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
                    showList(taskList.getTasks());
                } else if (userInput.toLowerCase().startsWith("mark")) {
                    handleMark(userInput, taskList.getTasks(), true);
                } else if (userInput.toLowerCase().startsWith("unmark")) {
                    handleMark(userInput, taskList.getTasks(), false);
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

    public static void showList(ArrayList<Task> taskList) throws RakanException {
        if (taskList.isEmpty()) {
            throw new RakanException("Nothing here yet!");
        } else {
            // use a StringBuilder to create the list
            StringBuilder list = new StringBuilder("Tasklist:");
            final int[] index = {1}; // use array to mutate inside lambda

            taskList.forEach(task -> {
                list.append("\n").append(index[0]).append(". ").append(task);
                index[0]++;
            });

            entry(list.toString());
        }
    }

    public static void handleMark(String input, ArrayList<Task> taskList, boolean isMark) throws RakanException, IOException
    {
        String keyword = isMark ? "mark" : "unmark";
        String[] parts = input.split("\\s+", 2);

        if (parts.length < 2) {
            throw new RakanException("Please provide a task number after \"" + keyword + "\".");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            int index = taskNumber - 1;

            // check if number provided is valid
            if (index < 0 || index >= taskList.size() || isNull(taskList.get(index))) {
                throw new RakanException("No such task: " + taskNumber);
            }

            Task task = taskList.get(index);

            if (isMark) {
                if (task.isDone) {
                    throw new RakanException("This task is already marked as done!");
                }
                task.markAsDone();
            } else {
                if (!task.isDone) {
                    throw new RakanException("This task is already marked as not done!");
                }
                task.markAsNotDone();
            }

            Storage.saveTasks(taskList);

            entry((isMark
                    ? "Nice! I've marked this task as done:"
                    : "OK, I've marked this task as not done yet:")
                + "\n" + task
            );
        } catch (NumberFormatException e) {
            throw new RakanException("Invalid task number: " + parts[1]);
        }
    }
    public static void entry(String message) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("---------------------------------------------------------------------------------");
    }
}
