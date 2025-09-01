package rakan.tasklist;

import rakan.RakanException;
import rakan.parser.ParsedMark;
import rakan.task.Task;

import java.io.IOException;
import java.util.ArrayList;

import static java.util.Objects.isNull;

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

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void handleDelete(int index) throws RakanException, IOException {

        try {
            // check if number provided is valid
            if (index < 0 || index >= tasks.size() || isNull(tasks.get(index))) {
                throw new RakanException("No such task: " + (index + 1));
            }
            tasks.remove(index);
        } catch (NumberFormatException e) {
            throw new RakanException("Invalid task number: " + (index + 1));
        }
    }

    public void handleMark(ParsedMark parsed) throws RakanException, IOException
    {
        Task task = tasks.get(parsed.getTaskIndex());

        if (parsed.isMark()) {
            if (task.isDone()) {
                throw new RakanException("This task is already marked as done!");
            }
            task.markAsDone();
        } else {
            if (!task.isDone()) {
                throw new RakanException("This task is already marked as not done!");
            }
            task.markAsNotDone();
        }

    }

    public ArrayList<Task> find (String keyword) {
        ArrayList<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword)) {
                results.add(task);
            }
        }

        return results;
    }
}
