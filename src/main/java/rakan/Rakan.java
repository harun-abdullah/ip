package rakan;

import rakan.command.Command;
import rakan.parser.Parser;
import rakan.storage.Storage;
import rakan.tasklist.TaskList;
import rakan.ui.Ui;

/**
 * Core class that drives the Rakan application.
 * <p>
 * This class coordinates between the {@link Parser}, {@link Command},
 * {@link TaskList}, {@link Storage}, and {@link Ui} components to
 * process user input, execute commands, and return responses to display in the GUI.
 * </p>
 */
public class Rakan {

    private Ui ui;
    private TaskList taskList;
    private Storage storage;
    private boolean shouldExit = false;

    /**
     * Constructs a new {@code Rakan} instance.
     *
     * @param filePath The file path used for persistent task storage.
     */
    public Rakan(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.loadTasks());
    }

    /**
     * Processes a single user input string and returns the corresponding response.
     * <p>
     * The input is parsed into a {@link Command}, executed on the
     * {@link TaskList}, and any output is collected via the {@link Ui}.
     * If an error occurs, its message is returned instead.
     * </p>
     *
     * @param userInput The raw input string entered by the user.
     * @return The formatted response to be displayed in the GUI.
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

    /**
     * Indicates whether the application should exit.
     *
     * @return {@code true} if the last command requested exit; {@code false} otherwise.
     */
    public boolean shouldExit() {
        return shouldExit;
    }

    /**
     * Returns the {@link Ui} instance associated with this Rakan session.
     *
     * @return The UI component.
     */
    public Ui getUi() {
        return ui;
    }
}
