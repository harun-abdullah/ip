import java.util.Scanner;

import static java.util.Objects.isNull;

public class Rakan {
    public static void main(String[] args) {
        greet();
        Scanner scanner = new Scanner(System.in);
        String[] stringList = new String[100];
        int counter = 0;

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                if (isNull(stringList[0])) {
                    System.out.println("Nothing here!");
                } else {
                    int i = 0;
                    while (!isNull(stringList[i])) {
                        System.out.println((i + 1) + ". " + stringList[i]);
                        i++;
                    }
                }
            } else {
                stringList[counter] = userInput;
                System.out.println("Added: " + userInput);
                counter++;
            }
        }
        exit();
    }

    public static void greet() {
        System.out.println("Wazzap. I'm Rakan \uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 \nHow can I help you?");
    }

    public static void exit() {
        System.out.println("Oh, bye then! See you later vro \uD83E\uDD40 \uD83E\uDD40 \uD83E\uDD40 ");
    }


}
