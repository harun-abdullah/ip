import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;

    public TaskList(ArrayList<Task> tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void createTodo(String input, Ui ui) throws RakanException, IOException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new RakanException("Hold on. The description of a todo cannot be empty.");
        }

        Task todo = new ToDo(description);
        tasks.add(todo);
        storage.saveTasks(tasks);
        ui.entry("Got it. I've added this task:\n  " + todo
                + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    public void createDeadline(String input, Ui ui) throws RakanException, IOException {
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
        tasks.add(deadline);
        storage.saveTasks(tasks);

        ui.entry("Got it. I've added this task:\n  "
                + deadline + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.");
    }

    public void createEvent(String input, Ui ui) throws RakanException, IOException {
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
        tasks.add(event);
        storage.saveTasks(tasks);

        ui.entry("Got it. I've added this task:\n  " + event
                + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    public void handleDelete(int index) throws RakanException, IOException {

        try {
            // check if number provided is valid
            if (index < 0 || index >= tasks.size() || isNull(tasks.get(index))) {
                throw new RakanException("No such task: " + (index + 1));
            }

            tasks.remove(index);
            storage.saveTasks(tasks);

        } catch (NumberFormatException e) {
            throw new RakanException("Invalid task number: " + (index + 1));
        }
    }

    public void handleMark(String input, boolean isMark, Ui ui) throws RakanException, IOException
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
            if (index < 0 || index >= tasks.size() || isNull(tasks.get(index))) {
                throw new RakanException("No such task: " + taskNumber);
            }

            Task task = tasks.get(index);

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

            storage.saveTasks(tasks);

            ui.entry((isMark
                    ? "Nice! I've marked this task as done:"
                    : "OK, I've marked this task as not done yet:")
                    + "\n" + task
            );
        } catch (NumberFormatException e) {
            throw new RakanException("Invalid task number: " + parts[1]);
        }
    }

}
