package rakan.command;

import rakan.RakanException;
import rakan.parser.ParsedMark;
import rakan.parser.Parser;
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
