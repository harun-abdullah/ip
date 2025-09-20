package rakan.command;

import rakan.RakanException;
import rakan.storage.Storage;
import rakan.task.Task;
import rakan.task.ToDo;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * In charge of creating ToDo task and saving it in the given tasklist.
 */
public class TodoCommand extends Command {

    /**
     * String containing description of the ToDo task to be created.
     */
    private String input;

    /**
     * Constructor for TodoCommand.
     *
     * @param input User input containing description of the ToDo task.
     */
    public TodoCommand(String input) {
        this.input = input;
    }

    /**
     * Creates new ToDo task with input.
     * Adds it in the given tasklist and saves the list to storage.
     * Displays Ui message to show successful ToDo task execution.
     *
     * @param tasks TaskList to add to.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     * @throws RakanException Exception for errors in adding and saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        String[] parts = input.split(" ", 2);

        String description = parts.length > 1 ? parts[1] : "";
        Task newTask = new ToDo(description);

        tasks.addTask(newTask);
        ui.showAddedTask(newTask, tasks);
        storage.saveTasks(tasks.getTasks());
    }
}
