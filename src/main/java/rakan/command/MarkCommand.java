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
public class MarkCommand extends Command {
    /**
     * String containing index of task to be marked in tasklist.
     */
    private String input;

    /**
     * Constructor for MarkCommand.
     *
     * @param input User input containing index of task to mark.
     */
    public MarkCommand(String input) {
        this.input = input;
    }

    /**
     * Marks given task as done.
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
        ParsedMark parsedMark = new ParsedMark(taskNum, true);
        tasks.handleMark(parsedMark);
        ui.showMessages(
                " Nice! I've marked this task as done:",
                "   " + tasks.getTasks().get(taskNum)
        );
        storage.saveTasks(tasks.getTasks());
    }
}
