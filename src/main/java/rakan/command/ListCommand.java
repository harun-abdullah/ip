package rakan.command;

import rakan.RakanException;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        ui.showList(tasks.getTasks());
    }
}
