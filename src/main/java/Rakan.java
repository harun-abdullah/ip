import java.util.Scanner;

import static java.util.Objects.isNull;

public class Rakan {
    public static void main(String[] args) {
        greet();
        Scanner scanner = new Scanner(System.in);
        Task[] taskList = new Task[100];
        int counter = 0;

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                if (isNull(taskList[0])) {
                    entry("Nothing here yet!");
                } else {
                    int i = 0;
                    String list = "Tasklist: ";
                    while (!isNull(taskList[i])) {
                        list = String.join("\n", list, (i + 1) + ".[" + taskList[i].getStatusIcon() + "] " + taskList[i].getDescription());
                        i++;
                    }
                    entry(list);
                }
            } else if (userInput.toLowerCase().startsWith("mark")) {
                handleMark(userInput, taskList, true);
            } else if (userInput.toLowerCase().startsWith("unmark")) {
                handleMark(userInput, taskList, false);
            } else {
                taskList[counter] = new Task(userInput);
                entry("Added: " + userInput);
                counter++;
            }
        }
        exit();
    }

    public static void handleMark(String input, Task[] taskList, boolean isMark) {
        String keyword = isMark ? "mark" : "unmark";
        String[] parts = input.split("\\s+", 2);

        if (parts.length < 2) {
            entry("Please provide a task number after \"" + keyword + "\".");
            return;
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            int index = taskNumber - 1;

            // check if number provided is valid
            if (index < 0 || index >= taskList.length || taskList[index] == null) {
                entry("No such task: " + taskNumber);
                return;
            }

            if (isMark) {
                if (taskList[index].isDone) {
                    entry("This task is already marked as done!");
                    return;
                }
                taskList[index].markAsDone();
            } else {
                if (!taskList[index].isDone) {
                    entry("This task is already marked as not done!");
                    return;
                }
                taskList[index].markAsNotDone();
            }
            entry((isMark
                    ? "Nice! I've marked this task as done:"
                    : "OK, I've marked this task as not done yet:")
                + "\n" + ("  [" + taskList[index].getStatusIcon() + "] " + taskList[index].getDescription())
            );
        } catch (NumberFormatException e) {
            entry("Invalid task number: " + parts[1]);
        }
    }


    public static void greet() {
        entry("Wazzap. I'm Rakan \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \nHow can I help you?");
    }

    public static void exit() {
        entry("Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40 ");
    }

    public static void entry(String message) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("---------------------------------------------------------------------------------");
    }
}
