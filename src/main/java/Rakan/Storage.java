package Rakan;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
    private String filePath;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public void saveTasks(ArrayList<Task> tasks) {

        try {
            ensureFileExists();
            try (FileWriter writer = new FileWriter(filePath)) {
                for (Task task : tasks) {
                    writer.write(serialize(task) + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

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

    // Convert a task to serialised string to store
    private String serialize(Task task) {
        StringBuilder sb = new StringBuilder();

        if (task instanceof Deadline d) {
            sb.append("D | ")
                    .append(task.isDone ? "1" : "0").append(" | ")
                    .append(task.description).append(" | ")
                    .append(d.by.format(DATE_FORMAT));
        } else if (task instanceof Event e) {
            sb.append("E | ")
                    .append(task.isDone ? "1" : "0").append(" | ")
                    .append(task.description).append(" | ")
                    .append(e.from.format(DATE_FORMAT)).append(" | ")
                    .append(e.to.format(DATE_FORMAT));
        } else if (task instanceof ToDo) {
            sb.append("T | ")
                    .append(task.isDone ? "1" : "0").append(" | ")
                    .append(task.description);
        }

        return sb.toString();
    }

    // Convert a line into a Rakan.Task object
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
                task = new Deadline(description, by);
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
