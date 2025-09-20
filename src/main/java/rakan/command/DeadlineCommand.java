package rakan.command;

import java.time.LocalDateTime;

import rakan.RakanException;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.task.DeadLine;
import rakan.task.Task;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * In charge of creating Deadline task and saving it in the given tasklist.
 */
public class DeadlineCommand extends Command {
    /**
     * String containing description and by datetime of the Deadline task to be created.
     */
    private String input;

    /**
     * Constructor for DeadlineCommand.
     *
     * @param input User input containing description and by datetime of the Deadline task.
     */
    public DeadlineCommand(String input) {
        this.input = input;
    }

    /**
     * Creates new Deadline task with input.
     * Adds it in the given tasklist and saves the list to storage.
     * Displays Ui message to show successful Deadline task execution.
     *
     * @param tasks TaskList to add to.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     * @throws RakanException Exception for errors in adding and saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        String[] deadlineParts = input.split(" /by ");
        String[] commandParts = deadlineParts[0].split(" ", 2);
        String deadlineDesc = commandParts.length > 1 ? commandParts[1] : "";
        LocalDateTime by = Parser.formatStringToDate(deadlineParts[1]);
        try {
            Task deadline = new DeadLine(deadlineDesc, by);
            tasks.addTask(deadline);
            ui.showAddedTask(deadline, tasks);
            storage.saveTasks(tasks.getTasks());
        } catch (Exception e) {
            throw new RakanException("Huh, something went wrong");
        }
    }
}
