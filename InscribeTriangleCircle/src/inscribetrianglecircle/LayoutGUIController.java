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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseAdapter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * FXML Controller class
 *
 * @author Christine Lofaso & Omran (Omi) Majumder
 */
public class LayoutGUIController implements Initializable, MouseListener, MouseMotionListener {
    
    // Declares variables for triangle sides and angles
    Double sideA, sideB, sideC, angAB, angBC, angCA;
    // Declares constant for the radius of the "circle track"
    final Double RADIUS = 250.0;
    
    // Imported objects from SceneBuilder
    @FXML
    private Circle circleTrack;
    @FXML
    private Circle circle1;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle3;
    @FXML
    private AnchorPane root;
    @FXML
    private Polygon triangle;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label userPrompt;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Positions angle labels at program start
        positionAngles();
    }    
    
    // Draws traingle polygon between anchor circles
    private void drawTriangle() {
        
        // Redefines vertices of triangle polygon with new anchor positions
        triangle.getPoints().setAll(
            circle1.getLayoutX() - 300, circle1.getLayoutY() - 320, 
            circle2.getLayoutX() - 300, circle2.getLayoutY() - 320, 
            circle3.getLayoutX() - 300, circle3.getLayoutY() - 320);
        
    }
    
    // Prints angles at correct locations with calculated values
    private void printAngles () {
        
        // Calls methods to calculate, position, and rename angle labels 
        calculateAngles();
        positionAngles();
        setLabels();
        
    }
    
    // Imported method from SceneBuilder called when anchor circle nodes are
    // dragged
    @FXML
    private void dragNode(javafx.scene.input.MouseEvent event) {
        
        // Declares variables for XY coordinate adjustment, similar triangle
        // hypotenuses, and ratio between similar triangles
        Double xAdj, yAdj, hypot, ratio;
        
        // Declares and initializes circle object with event source object
        Circle selectedCircle = (Circle)event.getSource();
       
        // Calculates hypotenuse of similar triangle formed at mouse drag
        // coordinates
        hypot = Math.sqrt(Math.pow(event.getSceneX() - 300, 2) + 
                Math.pow(event.getSceneY() - 300, 2));
        
        // Calculates ratio between similar triangles (one triangle has a
        // hypotenuse of the radius of the "circle track" and the other with a
        // hypotenuse formed from the mouse drag coordinates
        ratio = RADIUS / hypot;
        
        // Applies ratio between similar triangle hypotenuses to calculate 
        // XY coordinate adjustment values
        xAdj = ratio * (event.getSceneX() - 300);
        yAdj = ratio * (event.getSceneY() - 300);
        
        // Sets XY coordinates of anchor circle to the perimeter of the "circle
        // track" regardless of where the user drags the anchor circle
        selectedCircle.setLayoutX(300 + xAdj);
        selectedCircle.setLayoutY(300 + yAdj);
        
        // Calls methods to print angles and draw new triangle polygon while
        // anchor circle is dragged
        printAngles();
        drawTriangle();
        
    }
    
    // Positions angle label above specified anchor circle
    private void positionAngle(Circle circle, Label label) {
        
        // Declares variables for XY coordinate adjustment and ratio between 
        // similar triangles
        Double xAdj, yAdj, ratio;
        
        // Calculates ratio between similar triangles (one triangle has a
        // hypotenuse formed from the desired radius away from the center of the
        // "circle track" where the label will be positioned and the other with 
        // a hypotenuse of the radius of the "circle track"
        ratio = 200 / RADIUS;
        
        // Applies ratio between similar triangle hypotenuses to calculate 
        // XY coordinate adjustment values for label positioning
        xAdj = ratio * (circle.getLayoutX() - 300);
        yAdj = ratio * (circle.getLayoutY() - 300);
        
        // Sets XY coordinates of angle labels to an invisible radius within
        // the "circle track"
        label.setLayoutX(295 + xAdj);
        label.setLayoutY(300 + yAdj);
        
    }
    
    // Positions all labels with corresponding anchor circle
    private void positionAngles() {
        
        positionAngle(circle1, label1);
        positionAngle(circle2, label2);
        positionAngle(circle3, label3);
        
    }
    
    // Calculates angles between anchor circles
    private void calculateAngles() {
        
        // Calculates side lengths of triangle polygon from anchor circle
        // positions using distance between two points formula
        sideA = Math.sqrt(Math.pow(circle1.getLayoutX() - circle2.getLayoutX(), 2) 
                + Math.pow(circle1.getLayoutY() - circle2.getLayoutY(), 2));
        sideB = Math.sqrt(Math.pow(circle2.getLayoutX() - circle3.getLayoutX(), 2) 
                + Math.pow(circle2.getLayoutY() - circle3.getLayoutY(), 2));
        sideC = Math.sqrt(Math.pow(circle3.getLayoutX() - circle1.getLayoutX(), 2) 
                + Math.pow(circle3.getLayoutY() - circle1.getLayoutY(), 2));
        
        // Calculates angles between sides of triangle polygon using law of cosines
        angAB = Math.toDegrees(Math.acos((sideA * sideA + sideB * sideB - sideC * sideC) 
                / (2 * sideA * sideB)));
        angBC = Math.toDegrees(Math.acos((sideB * sideB + sideC * sideC - sideA * sideA) 
                / (2 * sideB * sideC)));
        angCA = Math.toDegrees(Math.acos((sideC * sideC + sideA * sideA - sideB * sideB) 
                / (2 * sideC * sideA)));
                
    }
    
    // Sets angle labels with calculated angle values
    private void setLabels() {
        
        // Sets angle labels with angle values rounded to nearest tenth
        label1.setText("" + Math.round(angCA * 10.0) / 10.0);
        label2.setText("" + Math.round(angAB * 10.0) / 10.0);
        label3.setText("" + Math.round(angBC * 10.0) / 10.0);
        
    }
    
    // Mouse listener abstract methods
    @Override
    public void mouseClicked(MouseEvent me) {
    }
    @Override
    public void mouseEntered(MouseEvent me) {
    }
    @Override
    public void mouseExited(MouseEvent me) {
    }
    @Override
    public void mousePressed(MouseEvent me) {
    }
    @Override
    public void mouseReleased(MouseEvent me) {
    }
    
    // Mouse motion listener abstract methods
    @Override
    public void mouseDragged(MouseEvent me) {
    }
    @Override
    public void mouseMoved(MouseEvent me) {
    }
    
}

