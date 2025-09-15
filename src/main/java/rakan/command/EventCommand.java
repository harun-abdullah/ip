package rakan.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import rakan.RakanException;
import rakan.parser.ParsedEvent;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.task.Event;
import rakan.task.Task;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class EventCommand extends Command {
    private String input;

    public EventCommand(String input) {
        this.input = input;
    }

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
