package rakan.ui;

import rakan.RakanException;
import rakan.task.Task;
import rakan.tasklist.TaskList;

import java.util.ArrayList;

/**
 * Handles formatting and assembling messages to be displayed in the GUI.
 * <p>
 * This class builds responses in a {@link StringBuilder}
 * which is displayed by the GUI.
 * </p>
 */
public class Ui {
    private StringBuilder responseBuilder;

    /**
     * Constructs a {@code Ui} with a fresh response buffer.
     */
    public Ui() {
        responseBuilder = new StringBuilder();
    }

    /**
     * Returns the greeting message displayed when Rakan starts.
     *
     * @return A greeting string.
     */
    public String greet() {
        return "Wazzap. I'm Rakan. \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25\nHow can I help you?";
    }

    /**
     * Appends the goodbye message when Rakan exits.
     */
    public void showExit() {
        responseBuilder.append("Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40");
    }

    /**
     * Appends a confirmation message after adding a task.
     *
     * @param task     The task that was added.
     * @param taskList The list containing all tasks (used to show total count).
     */
    public void showAddedTask(Task task, TaskList taskList) {
        int taskCount = taskList.getTasks().size();
        responseBuilder.append("Got it. I've added this task:\n");
        responseBuilder.append("  ").append(task).append("\n");
        responseBuilder.append("Now you have ").append(taskCount).append(" tasks in the list.\n");
    }

    /**
     * Displays all tasks in the provided list with indexes.
     *
     * @param taskList List of tasks to display.
     * @throws RakanException If the list is empty.
     */
    public void showList(ArrayList<Task> taskList) throws RakanException {
        if (taskList.isEmpty()) {
            throw new RakanException("Nothing here yet!");
        }

        responseBuilder.append("Tasklist:\n");

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            responseBuilder.append(i + 1).append(". ").append(task).append("\n");
        }
    }

    /**
     * Displays the results of a keyword search in the task list.
     *
     * @param taskList The list of matching tasks to display.
     * @throws RakanException If no matches are found.
     */
    public void showFindResults(ArrayList<Task> taskList) throws RakanException {
        if (taskList.isEmpty()) {
            throw new RakanException("No results found!");
        }

        responseBuilder.append("Here's the matching tasks, enjoy:\n");

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            responseBuilder.append(i + 1).append(". ").append(task).append("\n");
        }
    }

    /**
     * Appends a single message to the response buffer.
     *
     * @param message The message to append.
     */
    public void showMessage(String message) {
        responseBuilder.append(message).append("\n");
    }

    /**
     * Appends multiple messages to the response buffer.
     *
     * @param messages One or more messages to append.
     */
    public void showMessages(String... messages) {
        for (String message : messages) {
            responseBuilder.append(message).append("\n");
        }
    }

    /**
     * Returns the complete response built so far.
     *
     * @return The response string with leading/trailing whitespace trimmed.
     */
    public String getResponse() {
        return responseBuilder.toString().trim();
    }

    /**
     * Clears all previously stored messages in the response buffer.
     */
    public void clearMessages() {
        responseBuilder.setLength(0);
    }
}
