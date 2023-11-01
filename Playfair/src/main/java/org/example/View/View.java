package org.example.View;
import org.example.Model.Model;
import org.example.Model.NullException;

import java.util.*;


/**
 * The View class provides a user interface for interacting with the Playfair cipher application.
 * It allows users to input plaintext, a keyword, and a separator for encryption and decryption.
 */
public class View {
    final Scanner scanner = new Scanner(System.in);
    private String plaintext;
    private String key;
    private char separator;

    /**
     * Displays a prompt and allows the user to enter plaintext.
     */
    private void enterPlaintext() {
        System.out.println("Plaintext cannot include X");
        while(true){
            System.out.println("Enter plaintext: ");
            plaintext = scanner.nextLine();
        
            if(plaintext.toUpperCase().contains("X")){
                System.out.println("Plaintext cannot include 'X'. Please try again.");
            }else{
                break;
            }
        }
    }

    /**
     * Prompts the user to enter a keyword and ensures that the keyword does not contain repeating letters.
     * If the keyword contains repeating letters, the user is prompted to enter the keyword again.
     *
     * @throws IllegalStateException If the user repeatedly enters a keyword with repeating letters.
     */
    private void enterKeyword() {
        while (true) {
            System.out.println("Enter keyword: ");
            key = scanner.nextLine();

            // Check if the keyword contains repeating letters
            boolean hasRepeatingLetters = hasRepeatingLetters(key);

            if (!hasRepeatingLetters) {
                break; // Exit the loop if there are no repeating letters
            } else {
                System.err.println("Keyword cannot have two of the same letters. Please try again.");
            }
        }
    }


    /**
     * Checks if the given keyword contains repeating letters.
     *
     * @param keyword The keyword to be checked.
     * @return True if the keyword contains repeating letters, false otherwise.
     */
    private boolean hasRepeatingLetters(String keyword) {
        Set<Character> repeated = new HashSet<>();
        for (char letter : keyword.toCharArray()) {
            if (repeated.contains(letter)) {
                return true;
            }
            repeated.add(letter);
        }
        return false;
    }



    /**
     * Displays a prompt and allows the user to enter a separator letter.
     * The separator must be a single letter (A-Z or a-z).
     *
     * @throws IllegalStateException If the user repeatedly enters an invalid separator.
     */
    private void enterSeparator() {
        while (true) {
            System.out.println("Enter separator letter: ");
            String separatorInput = scanner.nextLine();

            if (separatorInput.matches("[A-Za-z]") && separatorInput.length() == 1) {
                separator = separatorInput.charAt(0);
                return;
            } else {
                System.err.println("Separator must be a single letter. Please try again.");
            }
        }
    }

    /**
     * Retrieves the plaintext entered by the user.
     *
     * @return The plaintext entered by the user.
     */
    private String getPlaintext() {
        return plaintext;
    }

    /**
     * Retrieves the keyword entered by the user.
     *
     * @return The keyword entered by the user.
     */
    private String getKeyword() {
        return key;
    }

    /**
     * Retrieves the separator letter entered by the user.
     *
     * @return The separator letter entered by the user.
     */
    private char getSeparator() {
        return separator;
    }

    /**
     * Facilitates the process of entering plaintext, keyword, and separator by invoking
     * respective methods for user input.
     */
    private void enterData() {
        enterPlaintext();
        enterKeyword();
        enterSeparator();
    }
    
    /**
    * Executes the Playfair cipher application. This method initializes the View and Model components,
    * collects user data, performs encryption, and displays the results.
    */
    public void execute() {
        try {
            View view = new View();
            Model model = new Model();
        
            view.enterData();
        
            String ciphertext = model.getPlayfairEncrypt(view.getPlaintext(), view.getKeyword(), view.getSeparator());
            System.out.println("Encrypted: " + ciphertext);
            String decryptedText = model.getPlayfairDecrypt(ciphertext, view.getKeyword(), view.getSeparator());
            System.out.println("Decrypted: " + decryptedText);
        } catch (NullException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
