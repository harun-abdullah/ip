package rakan.command;

import java.io.IOException;

import rakan.RakanException;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException;

    public boolean isExit() {
        return false;
    }
}
