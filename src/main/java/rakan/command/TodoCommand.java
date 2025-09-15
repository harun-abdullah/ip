package rakan.command;

import rakan.RakanException;
import rakan.storage.Storage;
import rakan.task.Task;
import rakan.task.ToDo;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class TodoCommand extends Command {

    private String input;

    public TodoCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RakanException {
        String[] parts = input.split(" ", 2);

        String description = parts.length > 1 ? parts[1] : "";
        String duration = null;

        Task newTask = new ToDo(description);

        tasks.addTask(newTask);
        ui.showAddedTask(newTask, tasks);
        storage.saveTasks(tasks.getTasks());
    }
}
