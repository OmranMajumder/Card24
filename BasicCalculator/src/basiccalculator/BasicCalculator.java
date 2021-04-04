/*
 * Omran (Omi) Majumder
 * Dr. Alrajab
 * BCS345 JAVA Programming
 * 22 March 2021
 * Assignment 4
 * This program is a basic calculator.
 */
package basiccalculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Omran (Omi) Majumder
 */
public class BasicCalculator extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
       
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("LayoutGUI.fxml"));
        
        stage.setScene(new Scene(root));
        stage.setTitle("Omi's Basic Calculator");
        stage.show();
        
    }
}
