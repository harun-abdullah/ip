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
                    System.out.println("Nothing here!");
                } else {
                    int i = 0;
                    while (!isNull(taskList[i])) {
                        System.out.println((i + 1) + ".[" + taskList[i].getStatusIcon() + "] " + taskList[i].getDescription());
                        i++;
                    }
                }
            } else if (userInput.toLowerCase().startsWith("mark")) {
                handleMark(userInput, taskList, true);
            } else if (userInput.toLowerCase().startsWith("unmark")) {
                handleMark(userInput, taskList, false);
            } else {
                taskList[counter] = new Task(userInput);
                System.out.println("Added: " + userInput);
                counter++;
            }
        }
        exit();
    }

    public static void handleMark(String input, Task[] taskList, boolean isMark) {
        String keyword = isMark ? "mark" : "unmark";
        String[] parts = input.split("\\s+", 2);

        if (parts.length < 2) {
            System.out.println("Please provide a task number after \"" + keyword + "\".");
            return;
        }

        try {
            int taskNumber = Integer.parseInt(parts[1]);
            int index = taskNumber - 1;

            // check if number provided is valid
            if (index < 0 || index >= taskList.length || taskList[index] == null) {
                System.out.println("No such task: " + taskNumber);
                return;
            }

            if (isMark) {
                if (taskList[index].isDone) {
                    System.out.println("This task is already marked as done!");
                    return;
                }
                taskList[index].markAsDone();
            } else {
                if (!taskList[index].isDone) {
                    System.out.println("This task is already marked as not done!");
                    return;
                }
                taskList[index].markAsNotDone();
            }
            System.out.println(isMark
                    ? "Nice! I've marked this task as done:"
                    : "OK, I've marked this task as not done yet:");
            System.out.println("  [" + taskList[index].getStatusIcon() + "] " + taskList[index].getDescription());

        } catch (NumberFormatException e) {
            System.out.println("Invalid task number: " + parts[1]);
        }
    }


    public static void greet() {
        System.out.println("Wazzap. I'm Rakan \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \nHow can I help you?");
    }

    public static void exit() {
        System.out.println("Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40 ");
    }
}
