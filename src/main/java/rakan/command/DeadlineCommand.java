package rakan.command;

import java.time.LocalDateTime;

import rakan.RakanException;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.task.DeadLine;
import rakan.task.Task;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class DeadlineCommand extends Command {
    private String input;

    public DeadlineCommand(String input) {
        this.input = input;
    }

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
