package rakan.command;

import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showExit();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
