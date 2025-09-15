package rakan.command;

import rakan.RakanException;
import rakan.parser.ParsedMark;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class UnmarkCommand extends Command {
    private String input;

    public UnmarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        String[] parts = input.split(" ");
        int taskNum = Integer.parseInt(parts[1]);
        ParsedMark parsedMark = new ParsedMark(taskNum, false);
        tasks.handleMark(parsedMark);
        ui.showMessages(
                " Okay, I've marked this task as done:",
                "   " + tasks.getTasks().get(taskNum - 1)
        );
        storage.saveTasks(tasks.getTasks());
    }
}
