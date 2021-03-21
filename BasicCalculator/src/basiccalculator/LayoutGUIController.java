/*
 * Omran (Omi) Majumder
 * Dr. Alrajab
 * BCS345 JAVA Programming
 * 22 March 2021
 * Assignment 4
 * This program is a basic calculator.
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
    
    // declaration and initialization of "container" variables for holding the
    // current operation, current input, and saved input
    private String currOpr = "DEFAULT";
    private String currInput = "";
    private String savedInput = "";
    
    // declaration and initialization of conditional counters and booleans
    // tracking button presses and input length
    private Integer digitCount = 0;
    private Boolean arithPressed = false;
    private Boolean resultPressed = false;
    
    // declaration and initialization of constant for max digit length
    final private Integer MAX_DIGITS = 17;
    
    // JavaFX objects imported from SceneBuilder
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
        
        // startup "zero" screen
        outputScrn.setText("0");
        
    }    
    
    // private method for formatting results; excises trailing zeros and decimal
    private String formatResult (String input) {
        
        while (input.endsWith("0") || input.endsWith(".") && 
                input.contains(".")) {
            input = input.substring(0, input.length() - 2);
        }
        
        return input;
        
    }
    
    // method to add digit to output screen
    @FXML
    private void addDigit(ActionEvent event) {
        
        // resets calculation if a digit is entered without an operation
        if (resultPressed == true && digitCount == 0) {
            outputScrn.clear();
            currInput = "";
            savedInput = "";
        }
        
        // condition to add digit if below the maximum number of digits
        if (digitCount <= MAX_DIGITS) {

            // condition to replace zero screen with input
            if (arithPressed == false && digitCount == 0) {
                outputScrn.clear();
            }
            
            // digit count incremented when digit added to current input
            digitCount++;
            
            // casting event to button
            Button btn = (Button)event.getSource();
            
            // selection statement to read entered input; increments current
            // input with added digit
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
            
            // outputs added digit to output screen
            outputScrn.setText(currInput);
            
        }
          
    }
    
    // method to calculate the result of arithmetic operations
    @FXML
    private void calculate(ActionEvent event) {
        
        // condition to calculate results; requires arithmetic input, current
        // input and saved input
        if (arithPressed != false && !currInput.equals("") && !savedInput.equals("")) {
            
            // updates boolean variables to reflect button presses and resets
            resultPressed = true;
            arithPressed = false;
            
            // stores output to current input
            currInput = outputScrn.getText();
            
            // stores double value of inputs in double variables
            double num1 = Double.parseDouble(currInput);
            double num2 = Double.parseDouble(savedInput);
            
            // selection statement to calculate results of arithmetic operation
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
            
            // resets digit counter, current operation, and saved input to 
            // default values
            digitCount = 0;
            currOpr = "DEFAULT";
            savedInput = "";
            
            // outputs results to output screen
            outputScrn.setText(formatResult(currInput));
            
        }
        
    }
    
    // method to add decimal point to input number string
    @FXML
    private void addDec(ActionEvent event) {
        
        // formats input string if starting with a decimal point
        if (digitCount == 0 || resultPressed == true) {
            currInput = "0.";
            resultPressed = false;
        }
        
        // prevents extraneous decimal points
        else if (!currInput.contains(".")) {
            currInput += ".";    
        }       
        
        // outputs number string with decimal point
        outputScrn.setText(currInput);
        
    }
    
    // method for assigning arithmetic operation
    @FXML
    private void arithOpr(ActionEvent event) {
        
        // condition to calculate results if arithmetic operations are "chained"
        if (digitCount != 0 && !savedInput.equals("") && arithPressed == true) {
            calculate(event);
        }        
        
        // updates boolean variables to reflect button presses and resets
        arithPressed = true;
        resultPressed = false;
        
        // assigns saved input with current input and resets current input and
        // digit coutner to default values
        savedInput = currInput;
        currInput = "";
        digitCount = 0;
        
        // selection statement to assign arithmetic operation
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
    
    // method for "clear" operations, "C," and "CE"
    @FXML
    private void clearOpr(ActionEvent event) {
        
        // resets result button press and current input to default values
        resultPressed = false;
        currInput = "";
        
        // selection statement to determine correct "clear" operation
        if (((Button)event.getSource()).getText().equals("C")) {
            // resets all inputs to default values; outputs "zero" screen
            savedInput = "";
            currOpr = "DEFAULT";
            arithPressed = false;
            digitCount = 0;
            outputScrn.setText("0");
        }
        // resets the last input to zero
        else {
            outputScrn.setText("0");
        }
             
    }
    
    // method to calculate percentages (ex. 72 + 5% = 75.6)
    @FXML
    private void percOpr(ActionEvent event) { 
        
        // condition for valid percent input; requires saved input
        if (!savedInput.equals("")) {
            // stores double value of inputs in variables
            double perc = Double.parseDouble(currInput);
            double num = Double.parseDouble(savedInput);
            // assigns current input with value of percent calculation
            currInput = Double.toString(perc * num / 100.0);
            // outputs result to screen (press "=" to complete operation)
            outputScrn.setText(formatResult(currInput));
            
            // updates boolean variable for result button press and resets
            // digit counter to default value
            resultPressed = true;
            digitCount = 0;
        }
        
    }
    
}
