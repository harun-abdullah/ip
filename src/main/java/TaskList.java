import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void createTodo(String input, Ui ui) throws RakanException, IOException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new RakanException("Hold on. The description of a todo cannot be empty.");
        }

        Task todo = new ToDo(description);
        tasks.add(todo);
        Storage.saveTasks(tasks);
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
        Storage.saveTasks(tasks);

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
        Storage.saveTasks(tasks);

        ui.entry("Got it. I've added this task:\n  " + event
                + "\nNow you have " + tasks.size() + " tasks in the list.");
    }
}
