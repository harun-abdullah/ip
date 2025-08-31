import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class Rakan {

    private Ui ui;

    public Rakan() {
        ui = new Ui();
    }

    public void run() {
        ui.greet();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = Storage.loadTasks();

        while (true) {
            String userInput = scanner.nextLine();

            try {
                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                } else if (userInput.equalsIgnoreCase("list")) {
                    showList(taskList);
                } else if (userInput.toLowerCase().startsWith("mark")) {
                    handleMark(userInput, taskList, true);
                } else if (userInput.toLowerCase().startsWith("unmark")) {
                    handleMark(userInput, taskList, false);
                } else if (userInput.toLowerCase().startsWith("delete")) {
                    handleDelete(userInput, taskList);
                } else if (userInput.toLowerCase().startsWith("todo")) {
                    createTodo(userInput, taskList);
                } else if (userInput.toLowerCase().startsWith("deadline")) {
                    createDeadline(userInput, taskList);
                } else if (userInput.toLowerCase().startsWith("event")) {
                    createEvent(userInput, taskList);
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

    public static void createTodo(String input, ArrayList<Task> taskList) throws RakanException, IOException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new RakanException("Hold on. The description of a todo cannot be empty.");
        }

        Task todo = new ToDo(description);
        taskList.add(todo);
        Storage.saveTasks(taskList);
        entry("Got it. I've added this task:\n  " + todo
                + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    public static void createDeadline(String input, ArrayList<Task> taskList) throws RakanException, IOException {
        String[] parts = input.substring(8).split("/by", 2);
        if (parts.length < 2) {
            throw new RakanException("Wait wait wait. The deadline command needs a description and a /by date.");
        }

        String description = parts[0].trim();
        String byString = parts[1].trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime byDateTime;
        try {
            byDateTime = LocalDateTime.parse(byString, formatter);
        } catch (DateTimeParseException e) {
            throw new RakanException("I don't understand that date format. Try d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
        }

        Task deadline = new Deadline(description, byDateTime);
        taskList.add(deadline);
        Storage.saveTasks(taskList);

        entry("Got it. I've added this task:\n  "
                + deadline + "\n"
                + "Now you have " + taskList.size() + " tasks in the list.");
    }

    public static void createEvent(String input, ArrayList<Task> taskList) throws RakanException, IOException {
        String[] parts = input.substring(5).split("/from", 2);
        if (parts.length < 2 || !parts[1].contains("/to")) {
            throw new RakanException("Hold your horses. The event command needs a description, /from and /to.");
        }

        String description = parts[0].trim();
        String[] times = parts[1].split("/to", 2);
        String from = times[0].trim();
        String to = times[1].trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime fromDateTime, toDateTime;
        try {
            fromDateTime = LocalDateTime.parse(from, formatter);
            toDateTime = LocalDateTime.parse(to, formatter);
        } catch (DateTimeParseException e) {
            throw new RakanException("I don't understand that date format. Try d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
        }

        Task event = new Event(description, fromDateTime, toDateTime);
        taskList.add(event);
        Storage.saveTasks(taskList);

        entry("Got it. I've added this task:\n  " + event
                + "\nNow you have " + taskList.size() + " tasks in the list.");
    }

    public static void handleDelete(String input, ArrayList<Task> taskList) throws RakanException, IOException {
        String[] parts = input.split("\\s+", 2);

        if (parts.length < 2) {
            throw new RakanException("Please provide a task number to delete.");
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            int index = taskNumber - 1;

            // check if number provided is valid
            if (index < 0 || index >= taskList.size() || isNull(taskList.get(index))) {
                throw new RakanException("No such task: " + taskNumber);
            }

            Task task = taskList.get(index);
            taskList.remove(index);
            Storage.saveTasks(taskList);

            entry("Yes boss. I've removed the task below:\n" +
                    task + "\n"
                    + "Now you have " + taskList.size() + " tasks in the list");

        } catch (NumberFormatException e) {
            throw new RakanException("Invalid task number: " + parts[1]);
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

    public static void greet() {
        entry("Wazzap. I'm Rakan \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25\nHow can I help you?");
    }

    public static void exit() {
        entry("Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40");
    }

    public static void entry(String message) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("---------------------------------------------------------------------------------");
    }
}
