package rakan.command;

import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * In charge of signaling the program to exit.
 */
public class ExitCommand extends Command {

    /**
     * Shows exit message via Ui.
     *
     * @param tasks TaskList to work with.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showExit();
    }

    /**
     * @return True to indicate program exit.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
