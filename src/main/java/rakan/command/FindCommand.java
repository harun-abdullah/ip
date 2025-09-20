package rakan.command;

import java.util.ArrayList;

import rakan.RakanException;
import rakan.storage.Storage;
import rakan.task.Task;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * In charge of deleting task from tasklist and saving updated list in storage.
 */
public class FindCommand extends Command {

    /**
     * String containing keyword of task to be found in tasklist.
     */
    private String keyword;

    /**
     * Constructor for FindCommand
     *
     * @param input String containing keyword of task to be found.
     */
    public FindCommand(String input) {
        String[] parts = input.split(" ", 2);
        this.keyword = parts.length > 1 ? parts[1] : "";
    }

    /**
     * Searches for tasks using keyword and displays results.
     *
     * @param tasks TaskList to search in for task.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     * @throws RakanException Exception for no task found.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        ArrayList<Task> results = tasks.find(keyword);
        ui.showFindResults(results);
    }
}
