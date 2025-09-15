package rakan;

import rakan.command.Command;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

public class Rakan {

    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    private boolean shouldExit = false;

    /**
     * Constructs Rakan instance.
     *
     * @param filePath File path for storage.
     */
    public Rakan(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadTasks());
    }

    /**
     * Returns a string response to be displayed in the GUI.
     * Also forms the main logic for Rakan.
     *
     * @param userInput User input String for Rakan to process.
     * @return String response.
     */
    public String getResponse(String userInput) {
        assert ui != null : "UI cannot be null";
        assert storage != null : "Storage cannot be null";
        assert taskList != null : "Tasklist cannot be null";
        try {
            ui.clearMessages();
            Command c = Parser.parse(userInput);

            c.execute(taskList, ui, storage);

            shouldExit = c.isExit();

            return ui.getResponse();

        } catch (RakanException e) {
            return e.getMessage();
        }
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    public Ui getUi() {
        return ui;
    }

}
