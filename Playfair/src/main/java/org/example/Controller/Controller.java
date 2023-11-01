package org.example.Controller;
import org.example.View.View;

/**
 * @author Sebastian Malisz
 * @version 1.0
 * The Controller class serves as the entry point for the Playfair cipher application.
 * It initializes the Model and initiates the execution of the Playfair cipher functionality.
 */
public class Controller {
    /**
     * The main method is the entry point of the application.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        View view = new View();
        view.execute(); 
    }
}

