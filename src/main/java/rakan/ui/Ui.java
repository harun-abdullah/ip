package rakan.ui;

import rakan.RakanException;
import rakan.task.Task;
import rakan.tasklist.TaskList;

import java.util.ArrayList;

public class Ui {
    private StringBuilder responseBuilder;

    public Ui() {
        responseBuilder = new StringBuilder();
    }

    /**
     * Returns greeting message when starting Rakan.
     *
     * @return Greeting message.
     */
    public String greet() {
        return "Wazzap. I'm Rakan. \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25\nHow can I help you?";
    }

    /**
     * Returns goodbye message when closing Rakan.
     *
     * @return Exit message.
     */
    public void showExit() {
        responseBuilder.append("Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40");
    }

    public void showAddedTask(Task task, TaskList taskList) {
        int taskCount = taskList.getTasks().size();
        responseBuilder.append("Got it. I've added this task:\n");
        responseBuilder.append("  ").append(task).append("\n");
        responseBuilder.append("Now you have ").append(taskCount).append(" tasks in the list.\n");
    }

    /**
     * Displays list of tasks with indexes.
     *
     * @param taskList List of tasks to be displayed.
     * @throws RakanException If list is empty.
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
     * Display results of find() from TaskList.
     *
     * @param taskList List of Tasks to be displayed.
     * @throws RakanException If no results are found after searching.
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

    public void showMessage(String message) {
        responseBuilder.append(message).append("\n");
    }

    public void showMessages(String... messages) {
        for (String message : messages) {
            responseBuilder.append(message).append("\n");
        }
    }

    public String getResponse() {
        return responseBuilder.toString().trim();
    }

    public void clearMessages() {
        responseBuilder.setLength(0);
    }
}
