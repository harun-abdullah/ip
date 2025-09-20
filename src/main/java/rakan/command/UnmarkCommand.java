package rakan.command;

import rakan.RakanException;
import rakan.parser.ParsedMark;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * In charge of marking task at given index, and saving the change to storage.
 */
public class UnmarkCommand extends Command {
    /**
     * String containing index of task to be marked in tasklist.
     */
    private String input;

    /**
     * Constructor for UnmarkCommand.
     *
     * @param input User input containing index of task to unmark.
     */
    public UnmarkCommand(String input) {
        this.input = input;
    }

    /**
     * Marks given task as not done.
     * Displays Ui message afterward and saves changes to storage.
     *
     * @param tasks TaskList to work with.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     * @throws RakanException Exception when task is already done.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        String[] split = input.split(" ");
        int taskNum = Parser.validateTaskNumber(split[1], tasks.getTasks().size()) - 1;
        ParsedMark parsedMark = new ParsedMark(taskNum, false);
        tasks.handleMark(parsedMark);
        ui.showMessages(
                " Okay, I've marked this task as undone:",
                "   " + tasks.getTasks().get(taskNum)
        );
        storage.saveTasks(tasks.getTasks());
    }
}
