package rakan.tasklist;

import rakan.RakanException;
import rakan.parser.ParsedMark;
import rakan.task.Task;

import java.util.ArrayList;

/**
 * Represents a list of {@link Task} objects.
 * <p>
 * Provides operations to add, delete, mark/unmark, and search tasks
 * within the list.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * Constructs a {@code TaskList} with an existing collection of tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the underlying current list of tasks.
     *
     * @return the task list
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add null task to tasklist";
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list at the specified index.
     *
     * @param index the zero-based index of the task to delete
     */
    public void handleDelete(int index) {
        assert index >= 0 && index < tasks.size() : "Delete index is out of bounds: " + index + ", size: " + tasks.size();
        tasks.remove(index);
    }

    /**
     * Marks or unmarks a task in the list, depending on the parsed input.
     *
     * @param parsed contains the index of the task and whether to mark or unmark it
     * @throws RakanException if the task is already in the requested marked/unmarked state
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
     * Finds all tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param keyword the search keyword
     * @return a list of matching tasks
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
