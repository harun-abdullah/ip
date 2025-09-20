package rakan.command;

import rakan.RakanException;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.task.Task;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * In charge of deleting task from tasklist and saving updated list in storage.
 */
public class DeleteCommand extends Command {

    /**
     * String containing index of task to be deleted from tasklist.
     */
    private String input;

    /**
     * Constructor for DeleteCommand.
     *
     * @param input User input containing task index in tasklist.
     */
    public DeleteCommand(String input) {
        this.input = input;
    }

    /**
     * Deletes task at given index in input from the tasklist.
     *
     * @param tasks TaskList to delete from.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     * @throws RakanException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {

        String[] split = input.split(" ");
        int index = Parser.validateTaskNumber(split[1], tasks.getTasks().size()) - 1;
        Task task = tasks.getTasks().get(index);
        tasks.handleDelete(index);
        ui.showMessages(
                " Noted. I've removed this task:",
                "   " + task,
                " Now you have " + tasks.getTasks().size() + " tasks in the list."
        );
        storage.saveTasks(tasks.getTasks());
    }
}
