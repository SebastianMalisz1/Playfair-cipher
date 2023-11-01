package org.example.Model;

import java.util.*;

/**
 * The Model class provides the core functionality for the Playfair cipher application.
 * It handles text preparation, key matrix generation, encryption, and decryption.
 */
public class Model {
    /**
     * Removes spaces and non-alphabetic characters from the given text and converts it to uppercase.
     *
     * @param text The text to be prepared.
     * @return The prepared text in uppercase without spaces and non-alphabetic characters.
     */
    private String prepareText(String text) {
        text = text.replaceAll("\\s+", "");
        text = text.replaceAll("[^A-Za-z]", "").toUpperCase();
        return text;
    }

    /**
     * Generates a key matrix for the Playfair cipher based on the provided key.
     *
     * @param key The keyword used for generating the key matrix.
     * @return The key matrix generated from the keyword.
     */
    private char[][] generateKeyMatrix(String key) {
        key = prepareText(key);
        Set<Character> keySet = new HashSet<>();
        char[][] keyMatrix = new char[5][5];

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWYZ"; //without X

        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (index < key.length()) {
                    keyMatrix[i][j] = key.charAt(index);
                    keySet.add(key.charAt(index));
                    index++;
                } else {
                    while (true) {
                        char letter = alphabet.charAt(0);
                        alphabet = alphabet.substring(1);
                        if (!keySet.contains(letter)) {
                            keyMatrix[i][j] = letter;
                            keySet.add(letter);
                            break;
                        }
                    }
                }
            }
        }

        return keyMatrix;
    }

    /**
     * Finds the position of a letter in the given matrix.
     *
     * @param matrix The matrix in which to find the position of the letter.
     * @param letter The letter to find in the matrix.
     * @return An array of two integers representing the row and column of the letter in the matrix.
     */
    private int[] findPosition(char[][] matrix, char letter) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == letter) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * Encrypts the given plaintext using the Playfair cipher algorithm.
     *
     * @param plaintext The plaintext to be encrypted.
     * @param key The encryption key.
     * @param separator The separator character used during encryption.
     * @return The encrypted ciphertext.
     * @throws NullException If the provided 'plaintext' or 'key' is null.
     */
    private String playfairEncrypt(String plaintext, String key, char separator) throws NullException{
        // Check for null references
        if (plaintext == null || key == null) {
            throw new NullException("Input 'plaintext' and 'key' must not be null.");
        }

        plaintext = prepareText(plaintext);
        separator = Character.toUpperCase(separator);
        char[][] keyMatrix = generateKeyMatrix(key);

        // Replace double letters with separator
        StringBuilder plaintextBuilder = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char first = plaintext.charAt(i);
            char second = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : separator;
            if (first == second) {
                second = separator;
                i--;
            }
            plaintextBuilder.append(first).append(second);
        }

        StringBuilder ciphertextBuilder = new StringBuilder();
        for (int i = 0; i < plaintextBuilder.length(); i += 2) {
            char first = plaintextBuilder.charAt(i);
            char second = plaintextBuilder.charAt(i + 1);
            int[] pos1 = findPosition(keyMatrix, first);
            int[] pos2 = findPosition(keyMatrix, second);

            if (pos1[0] == pos2[0]) {
                ciphertextBuilder.append(keyMatrix[pos1[0]][(pos1[1] + 1) % 5]);
                ciphertextBuilder.append(keyMatrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) {
                ciphertextBuilder.append(keyMatrix[(pos1[0] + 1) % 5][pos1[1]]);
                ciphertextBuilder.append(keyMatrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else {
                ciphertextBuilder.append(keyMatrix[pos1[0]][pos2[1]]);
                ciphertextBuilder.append(keyMatrix[pos2[0]][pos1[1]]);
            }
        }

        return ciphertextBuilder.toString();
    }


    /**
     * Decrypts the given ciphertext using the Playfair cipher algorithm.
     *
     * @param ciphertext The ciphertext to be decrypted.
     * @param key The encryption key.
     * @param separator The separator character used during encryption.
     * @return The decrypted plaintext.
     * @throws NullException If the provided 'ciphertext' or 'key' is null.
     */
    private String playfairDecrypt(String ciphertext, String key, char separator) throws NullException{
        // Check for null references
        if (ciphertext == null || key == null) {
            throw new NullException("Input 'ciphertext' and 'key' must not be null.");
        }

        char[][] keyMatrix = generateKeyMatrix(key);

        // Decrypt each pair
        StringBuilder plaintextBuilder = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char first = ciphertext.charAt(i);
            char second = ciphertext.charAt(i + 1);
            int[] pos1 = findPosition(keyMatrix, first);
            int[] pos2 = findPosition(keyMatrix, second);

            if (pos1[0] == pos2[0]) {
                plaintextBuilder.append(keyMatrix[pos1[0]][(pos1[1] - 1 + 5) % 5]);
                plaintextBuilder.append(keyMatrix[pos2[0]][(pos2[1] - 1 + 5) % 5]);
            } else if (pos1[1] == pos2[1]) {
                plaintextBuilder.append(keyMatrix[(pos1[0] - 1 + 5) % 5][pos1[1]]);
                plaintextBuilder.append(keyMatrix[(pos2[0] - 1 + 5) % 5][pos2[1]]);
            } else {
                plaintextBuilder.append(keyMatrix[pos1[0]][pos2[1]]);
                plaintextBuilder.append(keyMatrix[pos2[0]][pos1[1]]);
            }
        }

        // Replace the separator with an empty string
        return plaintextBuilder.toString().replace(String.valueOf(separator), "");
    }


   /**
    * Encrypts the provided plaintext using the Playfair cipher algorithm.
    *
    * @param plaintext  The plaintext to be encrypted.
    * @param key        The encryption key.
    * @param separator  The separator character used during encryption.
    * @return The encrypted ciphertext.
    * @throws NullException If the provided 'plaintext' or 'key' is null.
    */
    public String getPlayfairEncrypt(String plaintext, String key, char separator) throws NullException {
        return playfairEncrypt(plaintext, key, separator);
    }

    /**
    * Decrypts the provided ciphertext using the Playfair cipher algorithm.
    *
    * @param ciphertext The ciphertext to be decrypted.
    * @param key        The encryption key.
    * @param separator  The separator character used during encryption.
    * @return The decrypted plaintext.
    * @throws NullException If the provided 'ciphertext' or 'key' is null.
    */
    public String getPlayfairDecrypt(String ciphertext, String key, char separator) throws NullException {
       return playfairDecrypt(ciphertext, key, separator);
    }

}
