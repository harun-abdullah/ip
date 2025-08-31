package Rakan;

import Rakan.Tasks.Task;

import java.util.ArrayList;

public class Ui {

    public void entry(String message) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void greet() {
        entry("Wazzap. I'm Rakan.Rakan \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25\nHow can I help you?");
    }

    public void exit() {
        entry("Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40");
    }

    public void showList(ArrayList<Task> taskList) throws RakanException {
        if (taskList.isEmpty()) {
            throw new RakanException("Nothing here yet!");
        } else {
            StringBuilder list = new StringBuilder("Tasklist:");
            final int[] index = {1}; // use array to mutate inside lambda

            taskList.forEach(task -> {
                list.append("\n").append(index[0]).append(". ").append(task);
                index[0]++;
            });

            entry(list.toString());
        }
    }

}
