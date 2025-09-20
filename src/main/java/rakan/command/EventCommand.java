package rakan.command;

import java.time.LocalDateTime;

import rakan.RakanException;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.task.Event;
import rakan.task.Task;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * In charge of creating Deadline task and saving it in the given tasklist.
 */
public class EventCommand extends Command {
    /**
     * String containing description and by datetime of the Event task to be created.
     */
    private String input;

    /**
     * Constructor for EventCommand.
     *
     * @param input User input containing description and to/from datetimes of the Event task.
     */
    public EventCommand(String input) {
        this.input = input;
    }

    /**
     * Creates new Event task with input.
     * Adds it in the given tasklist and saves the list to storage.
     * Displays Ui message to show successful Event task execution.
     *
     * @param tasks TaskList to add to.
     * @param ui Ui object to display messages during execution.
     * @param storage Storage object used during task saving.
     * @throws RakanException Exception for errors in adding and saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        String[] fromSplit = input.split(" /from ");
        String[] commandParts = fromSplit[0].split(" ", 2);
        String eventDesc = commandParts.length > 1 ? commandParts[1] : "";
        String[] toSplit = fromSplit[1].split(" /to ");
        LocalDateTime from = Parser.formatStringToDate(toSplit[0]);
        LocalDateTime to = Parser.formatStringToDate(toSplit[1]);

        try {
            Task eventTask = new Event(eventDesc, from, to);
            tasks.addTask(eventTask);
            ui.showAddedTask(eventTask, tasks);
            storage.saveTasks(tasks.getTasks());
        } catch (Exception e) {
            throw new RakanException("Huh, something went wrong");
        }
    }
}
