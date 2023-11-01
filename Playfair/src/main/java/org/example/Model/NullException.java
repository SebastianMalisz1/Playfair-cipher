package org.example.Model;

/**
 * Exception class for objects thrown when attempting to use null parameters.
 */
public class NullException extends Exception {

    /**
     * Creates a new NullException.
     */
    public NullException() {
    }

    /**
     * Creates a new NullException with the specified error message.
     *
     * @param message The error message.
     */
    public NullException(String message) {
        super(message);
    }
}