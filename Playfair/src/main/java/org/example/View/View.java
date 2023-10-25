package org.example.View;

import java.util.*;

public class View {
    static Scanner scanner = new Scanner(System.in);
    private static String plaintext;
    private static String key;
    private static char separator;

    static void enterPlaintext() {
        System.out.println("Enter plaintext: ");
        plaintext = scanner.nextLine();
    }

    static void enterKeyword() {
        System.out.println("Enter keyword: ");
        key = scanner.next();
    }

    static void enterSeparator() {
        while (true) {
            System.out.println("Enter separator letter: ");
            String separatorInput = scanner.next();

            if (separatorInput.matches("[A-Za-z]") && separatorInput.length() == 1) {
                separator = separatorInput.charAt(0);
                return;
            } else {
                System.err.println("Separator must be a single letter. Please try again.");
            }
        }

    }
    public static String getPlaintext(){
        return plaintext;
    }

    public static String getKeyword(){
        return key;
    }

    public static char getSeparator() {
        return separator;
    }

    public static void enterData(){
        enterPlaintext();
        enterKeyword();
        enterSeparator();
    }
}
