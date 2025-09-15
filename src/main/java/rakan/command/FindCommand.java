package rakan.command;

import java.util.ArrayList;
import java.util.regex.Pattern;

import rakan.RakanException;
import rakan.storage.Storage;
import rakan.task.Task;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String input) {
        String[] parts = input.split(" ", 2);
        this.keyword = parts.length > 1 ? parts[1] : "";
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        ArrayList<Task> results = tasks.find(keyword);
        ui.showFindResults(results);
    }
}
