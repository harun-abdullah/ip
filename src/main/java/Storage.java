import java.io.*;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "./data/rakan.txt";

    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Task task : tasks) {
                writer.write(serialize(task) + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = deserialize(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    private static String serialize(Task task) {
        StringBuilder sb = new StringBuilder();

        if (task instanceof Deadline d) {
            sb.append("D | ")
                    .append(task.isDone ? "1" : "0").append(" | ")
                    .append(task.description).append(" | ")
                    .append(d.by);
        } else if (task instanceof Event e) {
            sb.append("E | ")
                    .append(task.isDone ? "1" : "0").append(" | ")
                    .append(task.description).append(" | ")
                    .append(e.from).append(" | ")
                    .append(e.to);
        } else if (task instanceof ToDo) {
            sb.append("T | ")
                    .append(task.isDone ? "1" : "0").append(" | ")
                    .append(task.description);
        }

        return sb.toString();
    }

    // Convert a line into a Task object
    private static Task deserialize(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;

        switch (type) {
            case "D": {
                String by = parts[3];
                task = new Deadline(description, by);
                break;
            }
            case "E": {
                String from = parts[3];
                String to = parts[4];
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
