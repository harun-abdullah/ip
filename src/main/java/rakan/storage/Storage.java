package rakan.storage;

import rakan.task.DeadLine;
import rakan.task.Event;
import rakan.task.Task;
import rakan.task.ToDo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Handles persistent storage of tasks.
 * <p>
 * Responsible for saving and loading {@link Task} objects
 * to and from a specified file in a simple text format.
 */
public class Storage {
    private String filePath;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Constructs a {@code Storage} instance with the given file path.
     * The file path determines where tasks are saved and loaded from.
     *
     * @param filePath the location of the save file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Ensures that the storage file and its parent directories exist.
     * Creates them if necessary.
     *
     * @throws IOException if the file or directories cannot be created
     */
    private void ensureFileExists() throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Saves the given list of tasks to the storage file.
     * Each task is serialized into a line of text.
     *
     * @param tasks the list of tasks to save
     */
    public void saveTasks(ArrayList<Task> tasks) {

        assert tasks != null : "Task list cannot be null";

        try {
            ensureFileExists();
            try (FileWriter writer = new FileWriter(filePath)) {
                for (Task task : tasks) {
                    assert task != null : "Individual tasks cannot be null while saving";
                    writer.write(serialize(task) + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file.
     * Each line is deserialized back into a {@link Task} object.
     *
     * @return a list of tasks, or an empty list if no file exists
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try {
            ensureFileExists();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Task task = deserialize(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Converts a {@link Task} into its string representation
     * suitable for storage in the save file.
     *
     * @param task the task to serialize
     * @return the serialized string
     */
    private String serialize(Task task) {
        StringBuilder sb = new StringBuilder();

        if (task instanceof DeadLine d) {
            sb.append("D | ")
                    .append(task.isDone() ? "1" : "0").append(" | ")
                    .append(task.getDescription()).append(" | ")
                    .append(d.getBy().format(DATE_FORMAT));
        } else if (task instanceof Event e) {
            sb.append("E | ")
                    .append(task.isDone() ? "1" : "0").append(" | ")
                    .append(task.getDescription()).append(" | ")
                    .append(e.getFrom().format(DATE_FORMAT)).append(" | ")
                    .append(e.getTo().format(DATE_FORMAT));
        } else if (task instanceof ToDo) {
            sb.append("T | ")
                    .append(task.isDone() ? "1" : "0").append(" | ")
                    .append(task.getDescription());
        }

        return sb.toString();
    }

    /**
     * Converts a line of text from the save file into a {@link Task} object.
     *
     * @param line the serialized task line
     * @return the deserialized {@link Task}, or {@code null} if parsing fails
     */
    private Task deserialize(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

        switch (type) {
            case "D": {
                LocalDateTime by = LocalDateTime.parse(parts[3], formatter);
                task = new DeadLine(description, by);
                break;
            }
            case "E": {
                LocalDateTime from = LocalDateTime.parse(parts[3], formatter);
                LocalDateTime to = LocalDateTime.parse(parts[4], formatter);
                task = new Event(description, from, to);
                break;
            }
            case "T": {
                task = new ToDo(description);
                break;
            }
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }
}
