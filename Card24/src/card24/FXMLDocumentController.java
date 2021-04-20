/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card24;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author omran
 */
public class FXMLDocumentController implements Initializable {
    String test = " (4 + 2)* 3 + 2";
    
    int userNum1, userNum2, userNum3, userNum4;
    
    @FXML
    private Rectangle cardBox1;
    @FXML
    private Rectangle cardBox2;
    @FXML
    private Rectangle cardBox3;
    @FXML
    private Rectangle cardBox4;
    @FXML
    private Label userPrompt;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField solutionText;
    @FXML
    private TextField userText;
    @FXML
    private Button solveButton;
    @FXML
    private Button checkButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        test = removeSpaces(test);
        
        System.out.println(test);
        System.out.println(evaluateExpression(test));
    }    
    
    public String removeSpaces(String input) {
        
        return input.replaceAll(" ", "");
        
    }
    
    public Boolean validateParentheses(String input) {
        
        int rightCount = 0, leftCount = 0;
        
        for (int i = 0; i < input.length() - 1; i++) {
           
            if (input.charAt(i) == '(') 
                leftCount++;
            
            else if (input.charAt(i) == ')')
                rightCount++;
            
        }
        
        if (rightCount == leftCount)           
            return true;
        
        else 
            return false;
            
    }
    
    public String evaluateExpression(String input) {
        
        int i, j;
        
        while (input.contains("(") || input.contains(")")) {
            // find outside paren, then matching right paren
            // index towards the middle and start solving outwards
            for (i = input.length() - 1; i >= 0; i--) {
                if (input.charAt(i) == '(')
                    break;
            }
            
            for (j = 0; j < input.length() - 1; j++) {
                if (input.charAt(j) == ')')
                    break;
            }
            
            Double tempDouble;
            String tempString;
            tempString = input.substring(i + 1, j);
            
            
            while (tempString.contains("*") || tempString.contains("/") 
                    || tempString.contains("+") || tempString.contains("-")) {
                
                ArrayList<String> operands = new ArrayList<String>();
                ArrayList<String> operators = new ArrayList<String>();
                
                for (int k = 0; k < tempString.length() - 1; k++) {
                    
                    if (k + 1 < tempString.length() - 1 
                            && Character.isDigit(tempString.charAt(k)) 
                            && Character.isDigit(tempString.charAt(k + 1))) {
                        
                        operands.add(Character.toString(tempString.charAt(k))
                                + tempString.charAt(k + 1));
                        
                        k++;
                        
                    }
                    
                    else if (Character.isDigit(tempString.charAt(k))) {
                        operands.add(Character.toString(tempString.charAt(k)));
                    }
                    
                    else {
                        operators.add(Character.toString(tempString.charAt(k)));
                    }
                    
                }
                
            }
            
            input = (input.substring(0, i)).concat(tempString).concat(input.substring(j + 1));
            
        }
        
        return input;
        
    }
    
}
