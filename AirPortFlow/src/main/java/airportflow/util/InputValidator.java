package airportflow.util;

import java.util.Scanner;

/**
 * Utility class for validating user input.
 * Provides simple input validation methods for the console application.
 */
public class InputValidator {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Reads a non-empty string from the user.
     *
     * @param prompt message shown to the user
     * @return the entered string
     */
    public static String readString(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return "";
        }
        String input = scanner.nextLine();
        while (input.trim().isEmpty()) {
            if (!scanner.hasNextLine()) {
                return "";
            }
            System.out.print("Input cannot be empty. " + prompt);
            input = scanner.nextLine();
        }
        return input.trim();
    }

    /**
     * Reads an integer within the given range [min, max].
     *
     * @param prompt message shown to the user
     * @param min    minimum allowed value (inclusive)
     * @param max    maximum allowed value (inclusive)
     * @return the entered integer
     */
    public static int readInt(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return min - 1;
            }
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                continue;
            }
            try {
                value = Integer.parseInt(input.trim());
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    /**
     * Reads a positive integer.
     *
     * @param prompt message shown to the user
     * @return the entered positive integer
     */
    public static int readPositiveInt(String prompt) {
        return readInt(prompt, 1, Integer.MAX_VALUE);
    }

    /**
     * Pauses and waits for the user to press Enter.
     */
    public static void pressEnterToContinue() {
        System.out.println();
        System.out.print("Press Enter to continue...");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    /**
     * Clears the console screen (works on most terminals).
     */
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print blank lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
