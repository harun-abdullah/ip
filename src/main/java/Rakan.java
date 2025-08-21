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

            try {
                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                } else if (userInput.equalsIgnoreCase("list")) {
                    if (isNull(taskList[0])) {
                        throw new RakanException("Nothing here yet!");
                    } else {
                        int i = 0;
                        String list = "Tasklist:";
                        while (!isNull(taskList[i])) {
                            list = String.join("\n", list, (i + 1) + ". " + taskList[i]);
                            i++;
                        }
                        entry(list);
                    }
                } else if (userInput.toLowerCase().startsWith("mark")) {
                    handleMark(userInput, taskList, true);
                } else if (userInput.toLowerCase().startsWith("unmark")) {
                    handleMark(userInput, taskList, false);
                } else if (userInput.toLowerCase().startsWith("todo")) {
                    String description = userInput.substring(4).trim();
                    if (description.isEmpty()) {
                        throw new RakanException("Hold on. The description of a todo cannot be empty.");
                    }
                    taskList[counter] = new ToDo(description);
                    entry("Got it. I've added this task:\n  " + taskList[counter]
                            + "\nNow you have " + (counter + 1) + " tasks in the list.");
                    counter++;
                } else if (userInput.toLowerCase().startsWith("deadline")) {
                    String[] parts = userInput.substring(8).split("/by", 2);
                    if (parts.length < 2) {
                        throw new RakanException("Wait wait wait. The deadline command needs a description and a /by date.");
                    }
                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    taskList[counter] = new Deadline(description, by);
                    entry("Got it. I've added this task:\n  " + taskList[counter]
                            + "\nNow you have " + (counter + 1) + " tasks in the list.");
                    counter++;
                } else if (userInput.toLowerCase().startsWith("event")) {
                    String[] parts = userInput.substring(5).split("/from", 2);
                    if (parts.length < 2 || !parts[1].contains("/to")) {
                        throw new RakanException("Hold your horses. The event command needs a description, /from and /to.");
                    }

                    String description = parts[0].trim();
                    String[] times = parts[1].split("/to", 2);
                    String from = times[0].trim();
                    String to = times[1].trim();
                    taskList[counter] = new Event(description, from, to);
                    entry("Got it. I've added this task:\n  " + taskList[counter]
                            + "\nNow you have " + (counter + 1) + " tasks in the list.");
                    counter++;

                } else {
                    throw new RakanException("Sorry, not sure what that means");
                }
            } catch (RakanException e) {
                entry(e.getMessage());
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
                + "\n" + taskList[index]
            );
        } catch (NumberFormatException e) {
            entry("Invalid task number: " + parts[1]);
        }
    }

    public static void greet() {
        entry("Wazzap. I'm Rakan \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25\nHow can I help you?");
    }

    public static void exit() {
        entry("Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40");
    }

    public static void entry(String message) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("---------------------------------------------------------------------------------");
    }
}
