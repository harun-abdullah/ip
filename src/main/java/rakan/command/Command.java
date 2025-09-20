package rakan.command;

import rakan.RakanException;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * Abstract Command class to execute the input commands given by the user after parsing.
 *
 */
public abstract class Command {

    /**
     * Executes the command specified by the input given during construction.
     *
     * @param tasks TaskList to work with.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     * @throws RakanException Exception for general errors.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException;

    /**
     * Boolean to indicate whether the program should close.
     * Is checked every time a command is run.
     *
     * @return False unless ExitCommand is executed.
     */
    public boolean isExit() {
        return false;
    }
}
