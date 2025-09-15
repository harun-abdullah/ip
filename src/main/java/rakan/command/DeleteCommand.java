package rakan.command;

import java.io.IOException;

import rakan.RakanException;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.task.Task;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class DeleteCommand extends Command {
    private String input;

    public DeleteCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {

        String[] split = input.split(" ");
        int index = Parser.validateTaskNumber(split[1], tasks.getTasks().size()) - 1;
        Task task = tasks.getTasks().get(index);
        try {
            tasks.handleDelete(index);
        } catch (IOException e) {
            throw new RakanException(e.getMessage());
        }
        ui.showMessages(
                " Noted. I've removed this task:",
                "   " + task,
                " Now you have " + tasks.getTasks().size() + " tasks in the list."
        );
        storage.saveTasks(tasks.getTasks());
    }
}
