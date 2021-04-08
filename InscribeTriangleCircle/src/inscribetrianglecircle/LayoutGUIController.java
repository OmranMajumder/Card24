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

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 * FXML Controller class
 *
 * @author Christine Lofaso & Omran (Omi) Majumder
 */
public class LayoutGUIController implements Initializable {
    
    // Declares variables for triangle sides and angles
    Double sideA, sideB, sideC, angAB, angBC, angCA, centX = 300.0, centY = 425.0;
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
    @FXML
    private Label mouseTracker;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        // Positions angle labels at program start
        printAngles();
        
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
    private void dragNode(MouseEvent event) {
        
        // Sets mouse coordinates when nodes are dragged
        setMouseLocation(event);
        
        
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
        
        // Declares variables for XY coordinates
        Double newX, newY;
        
        // Calls method to calculate the center coordinates of the triangle
        // polygon
        calculateTriangleCenter();
        
        // Determines coordinates 75% between the anchor circle coordinates and 
        // center coordinates of the triangle polygon
        newX = calculateMidpoint(circle.getLayoutX(), 
                calculateMidpoint(centX, circle.getLayoutX()));
        newY = calculateMidpoint(circle.getLayoutY(), 
                calculateMidpoint(centY, circle.getLayoutY()));
        
        // Sets XY coordinates at 75% between anchor circle and center of
        // triangle polygon
        label.setLayoutX(newX - 10);
        label.setLayoutY(newY - 10);
        
    }
    
    // Calculates midpoint between two X or two Y coordinates (average formula)
    private double calculateMidpoint(Double p1, Double p2) {
        
        // Declares variable for midpoint value
        double midX;
        
        // Averages input points
        midX = (p1 + p2) / 2.0;
        
        return midX;
        
    }
    
    // Calculates the center coordinates of the triangle polygon
    private void calculateTriangleCenter() {
        
        // Calculates center coordinates by average X and Y coordinates
        // of anchor circles
        centX = (circle1.getLayoutX() + circle2.getLayoutX() + circle3.getLayoutX())
                / 3;
        centY = (circle1.getLayoutY() + circle2.getLayoutY() + circle3.getLayoutY())
                / 3;
        
    }
    
    // Positions all labels with corresponding anchor circle
    private void positionAngles() {
        
        // Calls method to position angle labels around corresponding circle
        // anchor
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
    
    // Imported method from SceneBuilder called to track mouse location moving
    // within window and when mouse drag occurs
    @FXML
    private void setMouseLocation(MouseEvent event) {
        
        // Declares variable to restrict text to one decimal place
        DecimalFormat df = new DecimalFormat("#.0");
       
        // Sets text of label to continuously adjust coordinates based on mouse 
        // location within the scene
        mouseTracker.setText("Mouse Location: (" + df.format(event.getSceneX()) 
                + ", " + df.format(event.getSceneY()) + ")");
        
    }
    
}