/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inscribetrianglecircle;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseAdapter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * FXML Controller class
 *
 * @author omran
 */
public class LayoutGUIController implements Initializable, MouseListener, MouseMotionListener {
    Double sideA, sideB, sideC, angAB, angBC, angCA;
    final Double RADIUS = 250.0;
    
    @FXML
    private Circle circleTrack;
    @FXML
    private Circle circle1;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle3;
    private Label label;
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
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        positionAngles();
    }    
                
    
    
    
    
    private void drawTriangle() {

        triangle.getPoints().setAll(
            circle1.getLayoutX() - 300, circle1.getLayoutY() - 320, 
            circle2.getLayoutX() - 300, circle2.getLayoutY() - 320, 
            circle3.getLayoutX() - 300, circle3.getLayoutY() - 320);
        
    }
    
    private void printAngles () {
        
        calculateAngles();
        
        positionAngles();
        
        setLabels();
        
    }

    @FXML
    
    private void moveNode(javafx.scene.input.MouseEvent event) {
        /*
        Double xAdj, yAdj, hypot, ratio;
        
        Circle selectedCircle = (Circle)event.getSource();
        
        
        hypot = Math.sqrt(Math.pow(event.getSceneX() - 300, 2) + 
                Math.pow(event.getSceneY() - 300, 2));

        ratio = RADIUS / hypot;
        xAdj = ratio * (event.getSceneX() - 300);
        yAdj = ratio * (event.getSceneY() - 300);
        
        selectedCircle.setLayoutX(300 + xAdj);
        selectedCircle.setLayoutY(300 + yAdj);
        */
    }

    @FXML
    private void dragNode(javafx.scene.input.MouseEvent event) {
        
        Double xAdj, yAdj, hypot, ratio;
        
        Circle selectedCircle = (Circle)event.getSource();
       
        hypot = Math.sqrt(Math.pow(event.getSceneX() - 300, 2) + 
                Math.pow(event.getSceneY() - 300, 2));

        ratio = RADIUS / hypot;
        xAdj = ratio * (event.getSceneX() - 300);
        yAdj = ratio * (event.getSceneY() - 300);
        
        selectedCircle.setLayoutX(300 + xAdj);
        selectedCircle.setLayoutY(300 + yAdj);
        
        printAngles();
        drawTriangle();
        
    }
    
    private void positionAngle(Circle circle, Label label) {
        Double xAdj, yAdj, ratio;

        ratio = 200 / RADIUS;
        xAdj = ratio * (circle.getLayoutX() - 300);
        yAdj = ratio * (circle.getLayoutY() - 300);
        
        label.setLayoutX(295 + xAdj);
        label.setLayoutY(300 + yAdj);
        
    }
    
    private void positionAngles() {
        
        positionAngle(circle1, label1);
        positionAngle(circle2, label2);
        positionAngle(circle3, label3);
        
    }
    
    private void calculateAngles() {
        
        sideA = Math.sqrt(Math.pow(circle1.getLayoutX() - circle2.getLayoutX(), 2) + Math.pow(circle1.getLayoutY() - circle2.getLayoutY(), 2));
        sideB = Math.sqrt(Math.pow(circle2.getLayoutX() - circle3.getLayoutX(), 2) + Math.pow(circle2.getLayoutY() - circle3.getLayoutY(), 2));
        sideC = Math.sqrt(Math.pow(circle3.getLayoutX() - circle1.getLayoutX(), 2) + Math.pow(circle3.getLayoutY() - circle1.getLayoutY(), 2));
        
        angAB = Math.toDegrees(Math.acos((sideA * sideA + sideB * sideB - sideC * sideC) / (2 * sideA * sideB)));
        angBC = Math.toDegrees(Math.acos((sideB * sideB + sideC * sideC - sideA * sideA) / (2 * sideB * sideC)));
        angCA = Math.toDegrees(Math.acos((sideC * sideC + sideA * sideA - sideB * sideB) / (2 * sideC * sideA)));
        
        System.out.println("angCA: " + angCA + " angAB: " + angAB + " angBC: " + angBC);
        
    }
    
    private void setLabels() {
        
        label1.setText("" + Math.round(angCA * 10.0) / 10.0);
        label2.setText("" + Math.round(angAB * 10.0) / 10.0);
        label3.setText("" + Math.round(angBC * 10.0) / 10.0);
        
    }
    
    public void circlesToFront() {
        
        circle1.toFront();
        circle2.toFront();
        circle3.toFront();
        
    }
    
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
    
    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseMoved(MouseEvent me) {
 
    }
    
}

