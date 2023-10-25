package org.example.Model;
import org.example.View.View;

import java.util.*;
public class Model {
        private static String prepareText(String text) { //approved
            return text.replaceAll("[^A-Za-z ]", "").toUpperCase();
        }

    private static char[][] generateKeyMatrix(String key) { //approved
            key = prepareText(key);
            Set<Character> keySet = new HashSet<>();
            char[][] keyMatrix = new char[5][5];

            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWYZ";

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

        private static int[] findPosition(char[][] matrix, char letter) {
            // Find the position of a letter in the matrix
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (matrix[i][j] == letter) {
                        return new int[]{i, j};
                    }
                }
            }
            return null;
        }



        private static String playfairEncrypt(String plaintext, String key, char separator) {
            plaintext = prepareText(plaintext);
            char[][] keyMatrix = generateKeyMatrix(key);

            // Replace double letters with separator
            StringBuilder plaintextBuilder = new StringBuilder();
            for (int i = 0; i < plaintext.length(); i += 2) {
                char first = plaintext.charAt(i);
                char second = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : separator;
                if (first == second) {
                    second = separator;
                    i--;  // Repeat the current character
                }
                plaintextBuilder.append(first).append(second);
            }

            // Encrypt each pair
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

        private static String playfairDecrypt(String ciphertext, String key, char separator) {
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

        public static void execute(){

            View.enterData();
            String ciphertext = Model.playfairEncrypt(View.getPlaintext(), View.getKeyword(), View.getSeparator());
            System.out.println("Encrypted: " + ciphertext);

            String decryptedText = Model.playfairDecrypt(ciphertext, View.getKeyword(), View.getSeparator());
            System.out.println("Decrypted: " + decryptedText);
        }
}
