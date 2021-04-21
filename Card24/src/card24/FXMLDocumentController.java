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
    
    public int findLastLeftParen(String input) {
        
        int i;
        
        for (i = input.length() - 1; i >= 0; i--) {
                if (input.charAt(i) == '(')
                    break;
            }
        
        return i;
        
    }
    
    public int findFirstRightParen(String input) {

        int i;

        for (i = input.length() - 1; i >= 0; i--) {
            if (input.charAt(i) == ')')
                break;
        }

        return i;

    }
    
    public String evaluateParen(String input) {
        
        int i, j;
        String tempString;
            
        i = findLastLeftParen(input);
        j = findFirstRightParen(input);

        tempString = input.substring(i + 1, j);

        tempString = evaluateExpression(tempString);

        input = (input.substring(0, i)).concat(tempString).concat(input.substring(j + 1));
        
        return input;
        
    }

    public String evaluateExpression(String input) {
        
        if (input.contains("(")) {
            
            input = evaluateParen(input);
            input = evaluateExpression(input);
            
        }
        
        else {
            
            ArrayList<String> operands = new ArrayList<String>();
            ArrayList<String> operators = new ArrayList<String>();
        
            while (input.contains("*") || input.contains("/") 
                    || input.contains("+") || input.contains("-")) {

                sortOperatorsOperands(input, operators, operands);
                input = solveArithmetic(operators, operands);

            }
            
        }
        
        return input;
         
    }
    
    public String solveArithmetic(ArrayList<String> operators, ArrayList<String> operands) {
        
        Integer result = 0;
            
        for (int i = 0; i < operators.size(); i++) {

            if (operators.get(i).equals("*") || operators.get(i).equals("/")) {

                if (operators.get(i).equals("*"))
                    result = Integer.parseInt(operands.get(i)) * Integer.parseInt(operands.get(i + 1));

                else
                    result = Integer.parseInt(operands.get(i)) / Integer.parseInt(operands.get(i + 1));
                
                operands.set(i + 1, result.toString());

            }

            if (operators.get(i).equals("+") || operators.get(i).equals("-")) {

                if (operators.get(i).equals("+"))
                    result = Integer.parseInt(operands.get(i)) + Integer.parseInt(operands.get(i + 1));

                else
                    result = Integer.parseInt(operands.get(i)) - Integer.parseInt(operands.get(i + 1));
                
                operands.set(i + 1, result.toString());

            }

        }
        
        return result.toString();
        
    }
    
    public void sortOperatorsOperands(String input, ArrayList<String> operators, ArrayList<String> operands) {
        
        for (int i = 0; i < input.length(); i++) {

            if (i + 1 < input.length() 
                    && Character.isDigit(input.charAt(i)) 
                    && Character.isDigit(input.charAt(i + 1))) {

                operands.add(Character.toString(input.charAt(i))
                        + input.charAt(i + 1));

                i++;

            }

            else if (Character.isDigit(input.charAt(i))) {
                operands.add(Character.toString(input.charAt(i)));
            }

            else {
                operators.add(Character.toString(input.charAt(i)));
            }

        }
        
    }
    
    
}
