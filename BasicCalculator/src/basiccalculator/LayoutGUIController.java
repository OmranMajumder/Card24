/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basiccalculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Omran (Omi) Majumder
 */
public class LayoutGUIController implements Initializable {
    
    private String currOpr = "DEFAULT";
    private String currInput = "";
    private String savedInput = "";
    
    final private Integer MAX_DIGITS = 17;
    
    private Integer digitCount = 0;
    
    private Boolean arithPressed = false;
    private Boolean resultPressed = false;
    
    @FXML
    private TextField outputScrn;
    @FXML
    private Button zeroBtn;
    @FXML
    private Button enterBtn;
    @FXML
    private Button decimalBtn;
    @FXML
    private Button multBtn;
    @FXML
    private Button minusBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button clearEntryBtn;
    @FXML
    private Button percentBtn;
    @FXML
    private Button divBtn;
    @FXML
    private Button sevenBtn;
    @FXML
    private Button eightBtn;
    @FXML
    private Button fourBtn;
    @FXML
    private Button oneBtn;
    @FXML
    private Button fiveBtn;
    @FXML
    private Button nineBtn;
    @FXML
    private Button sixBtn;
    @FXML
    private Button twoBtn;
    @FXML
    private Button threeBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        outputScrn.setText("0");
        
    }    
    
    private String formatResult (String input) {
        
        while (input.endsWith("0") || input.endsWith(".") && 
                input.contains(".")) {
            input = input.substring(0, input.length() - 2);
        }
        
        return input;
        
    }
    
    @FXML
    private void addDigit(ActionEvent event) {
        
        if (resultPressed == true && digitCount == 0) {
            outputScrn.clear();
            currInput = "";
            savedInput = "";
        }
        
        if (digitCount <= MAX_DIGITS) {

            // condition to replace zero screen
            if (arithPressed == false && digitCount == 0) {
                outputScrn.clear();
            }
                     
            digitCount++;
            
            Button btn = (Button)event.getSource();

            switch (btn.getText()) {
                case "1": 
                    currInput += "1";
                    break;
                case "2": 
                    currInput += "2";
                    break;
                case "3": 
                    currInput += "3";
                    break;
                case "4": 
                    currInput += "4";
                    break;
                case "5": 
                    currInput += "5";
                    break;
                case "6": 
                    currInput += "6";
                    break;
                case "7": 
                    currInput += "7";
                    break;
                case "8": 
                    currInput += "8";
                    break;
                case "9": 
                    currInput += "9";
                    break;
                case "0": 
                    currInput += "0";
                    break;
            }

            outputScrn.setText(currInput);
            
        }
          
    }
    
    @FXML
    private void calculate(ActionEvent event) {
        
        if (arithPressed != false && digitCount != 0 && !savedInput.equals("")) {
            
            resultPressed = true;
            arithPressed = false;

            currInput = outputScrn.getText();
            
            double num1 = Double.parseDouble(currInput);
            double num2 = Double.parseDouble(savedInput);
            
            switch (currOpr) {
                case "ADD":
                    currInput = "" + (num2 + num1);
                    break;
                case "MINUS":
                    currInput = "" + (num2 - num1);
                    break;
                case "MULT":
                    currInput = "" + (num2 * num1);
                    break;
                case "DIV":
                    currInput = "" + (num2 / num1);
                    break;              
            }
            
            digitCount = 0;
            
            currOpr = "DEFAULT";
            savedInput = "";
            
            outputScrn.setText(formatResult(currInput));
            
        }
        
    }

    @FXML
    private void addDec(ActionEvent event) {
        
        if (digitCount == 0 || resultPressed == true) {
            currInput = "0.";
            resultPressed = false;
        }
        
        else if (!currInput.contains(".")) {
            currInput += ".";    
        }       
        
        outputScrn.setText(currInput);
        
    }

    @FXML
    private void arithOpr(ActionEvent event) {
        
        if (digitCount != 0 && !savedInput.equals("") && arithPressed == true) {
            calculate(event);
        }        
        
        arithPressed = true;
        resultPressed = false;
        
        savedInput = currInput;
        currInput = "";
        
        digitCount = 0;
        
        switch (((Button)event.getSource()).getText()) {
            case "+":
                currOpr = "ADD";
                break;
            case "-":
                currOpr = "MINUS";
                break;
            case "×":
                currOpr = "MULT";
                break;
            case "÷":
                currOpr = "DIV";
                break;
        }
        
    }

    @FXML
    private void clearOpr(ActionEvent event) {
        
        resultPressed = false;
        currInput = "";
        
        if (((Button)event.getSource()).getText().equals("C")) {
            savedInput = "";
            currOpr = "DEFAULT";
            outputScrn.setText("0");
            resetCounts();
        }
        else {
            outputScrn.setText("0");
        }
             
    }

    @FXML
    private void percOpr(ActionEvent event) { 
        
        if (!savedInput.equals("")) {
            double perc = Double.parseDouble(currInput);
            double num = Double.parseDouble(savedInput);
            currInput = Double.toString(perc * num / 100.0);
            outputScrn.setText(formatResult(currInput));
            
            resultPressed = true;
            digitCount = 0;
        }
        
    }
    
    private void resetCounts() {
        arithPressed = false;
        digitCount = 0;
    }
    
}
