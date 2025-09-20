package rakan.command;

import rakan.RakanException;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * In charge of listing all tasks in current tasklist.
 */
public class ListCommand extends Command {

    /**
     * Displays tasklist via Ui.
     *
     * @param tasks TaskList to display.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     * @throws RakanException Exception when no tasks to display.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        ui.showList(tasks.getTasks());
    }
}
