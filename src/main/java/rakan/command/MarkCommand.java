package rakan.command;

import rakan.RakanException;
import rakan.parser.ParsedMark;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class MarkCommand extends Command {
    private String input;

    /**
     * Creates a mark command from the user's input.
     *
     * @param input the full command string like "mark 3"
     */
    public MarkCommand(String input) {
        this.input = input;
    }

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
