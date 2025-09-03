package rakan.ui;

import rakan.RakanException;
import rakan.task.Task;

import java.util.ArrayList;

public class Ui {

    /**
     * Prints a message out to the terminal.
     * The message has borders above and below.
     *
     * @param message Message to be printed out.
     */
    public void entry(String message) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("---------------------------------------------------------------------------------");
    }

    /**
     * Greeting message when starting Rakan.
     */
    public String greet() {
        return "Wazzap. I'm Rakan.Rakan \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25\nHow can I help you?";
    }

    /**
     * Exit message when closing Rakan.
     */
    public String exit() {
        return "Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40";
    }

    /**
     * Displays list of tasks with indexes.
     *
     * @param taskList List of tasks to be displayed.
     * @throws RakanException If list is empty.
     */
    public String showList(ArrayList<Task> taskList) throws RakanException {
        if (taskList.isEmpty()) {
            throw new RakanException("Nothing here yet!");
        } else {
            StringBuilder list = new StringBuilder("Tasklist:");
            final int[] index = {1}; // use array to mutate inside lambda

            taskList.forEach(task -> {
                list.append("\n").append(index[0]).append(". ").append(task);
                index[0]++;
            });

            return list.toString();
        }
    }

    /**
     * Display results of find() from TaskList.
     *
     * @param taskList List of Tasks to be displayed.
     * @throws RakanException If no results are found after searching.
     */
    public String showFindResults(ArrayList<Task> taskList) throws RakanException {
        if (taskList.isEmpty()) {
            throw new RakanException("No results found!");
        } else {
            StringBuilder list = new StringBuilder("Here's the matching tasks, enjoy:");
            final int[] index = {1}; // use array to mutate inside lambda

            taskList.forEach(task -> {
                list.append("\n").append(index[0]).append(". ").append(task);
                index[0]++;
            });

            return list.toString();
        }
    }

}
