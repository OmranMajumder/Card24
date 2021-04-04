/*
 * Christine Lofaso & Omran (Omi) Majumder
 * Dr. Alrajab
 * BCS345 JAVA Programming
 * 7 April 2021
 * Week 9 Assignment
 * This program displays the angles of a triangle inscribed in a circle with 
 * draggable vertices.
 */

package inscribetrianglecircle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Christine Lofaso & Omran (Omi) Majumder
 */
public class InscribeTriangleCircle extends Application {

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
        stage.setTitle("Inscribed Triangle");
        stage.show();
    }
    
}
