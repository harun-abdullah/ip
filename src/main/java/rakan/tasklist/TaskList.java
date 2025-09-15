package rakan.tasklist;

import rakan.RakanException;
import rakan.parser.ParsedMark;
import rakan.task.Task;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * Constructs tasklist.
     *
     * @param tasks list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the tasklist.
     *
     * @param task Task to be added.
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add null task to tasklist";
        tasks.add(task);
    }

    /**
     * Deletes specified task from the tasklist.
     *
     * @param index Index of selected task.
     */
    public void handleDelete(int index) {
        assert index >= 0 && index < tasks.size() : "Delete index is out of bounds: " + index + ", size: " + tasks.size();
        tasks.remove(index);
    }

    /**
     * Marks or unmarks specified task in the tasklist.
     *
     * @param parsed Contains index and whether it will be marked or unmarked.
     * @throws RakanException If task is already marked as done or not.
     */
    public void handleMark(ParsedMark parsed) throws RakanException {
        assert parsed.getTaskIndex() >= 0 && parsed.getTaskIndex() < tasks.size() :
                "Mark index out of bounds: " + parsed.getTaskIndex() + ", size: " + tasks.size();
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

    /**
     * Returns an ArrayList<Task> that match keyword.
     *
     * @param keyword String to use for search.
     * @return ArrayList of Tasks.
     */
    public ArrayList<Task> find(String keyword) {
        assert keyword != null : "Find keyword cannot be null";
        ArrayList<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword)) {
                results.add(task);
            }
        }

        return results;
    }
}
