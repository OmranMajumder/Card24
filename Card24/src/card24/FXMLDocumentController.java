/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card24;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author omran
 */
public class FXMLDocumentController implements Initializable {
    String test = " (4 + 2)* 3 + 2";
    
    ArrayList<Rectangle> cards = new ArrayList();
    ArrayList<String> cardCodes = new ArrayList();
    ArrayList<Integer> validValues = new ArrayList();
    ArrayList<String> solutionOperators = new ArrayList();

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
        
        initializeCards();
        drawCards(cardCodes);
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

            else if (Character.isDigit(input.charAt(i))) 
                operands.add(Character.toString(input.charAt(i)));
            
            else 
                operators.add(Character.toString(input.charAt(i)));
            
        }
        
    }
    
    public void setCardFaces(ArrayList<String> cardsDrawn) {
        
        for (int i = 0; i < cardsDrawn.size(); i++) {
            
            Image img = new Image("file:PlayingCards/" + cardsDrawn.get(i) + ".png");
            cards.get(i).setFill(new ImagePattern(img));
            
        }
        
        storeCardValues(cardsDrawn);
        
    }
    
    public void initializeCards() {
        
        cards.add(cardBox1);
        cards.add(cardBox2);
        cards.add(cardBox3);
        cards.add(cardBox4);
        
    }
    
    public void storeCardValues(ArrayList<String> cardsDrawn) {
        
        for (int i = 0; i < cardsDrawn.size(); i++) {
            
            validValues.add(Integer.parseInt(cardsDrawn.get(i).substring(1)));
            
        }

    }
    
    public void endGame() {
        
        // Add to user game count and user results count
        cardCodes.clear();
        validValues.clear();
        solutionText.clear();
        userText.clear();
        // Run random number generator again
        
    }
    
    public void drawCards(ArrayList<String> cardCodes) {
        
        Integer suit, cardValue;
        String cardCode;
        
        Random randGen = new Random();
        for (int i = 0; i < 4; i++) {
            
            suit = randGen.nextInt(4) + 1;
            cardValue = randGen.nextInt(13) + 1;
            
            cardCode = Integer.toString(suit) + Integer.toString(cardValue);
            
            if (i == 0)
                cardCodes.add(cardCode);
            
            else if (i != 0 && !(cardCode.equals(cardCodes.get(i - 1))))
                cardCodes.add(cardCode);
            
            else
                i--;

        }
        
        setCardFaces(cardCodes);
        
    }

    @FXML
    private void findSolution(ActionEvent event) {
        solutionFinder(validValues);
    }

    @FXML
    private void redrawCards(ActionEvent event) {
        endGame();
        drawCards(cardCodes);
    }

    @FXML
    private void verifyExpression(ActionEvent event) {
    }
    
    public void solutionFinder(ArrayList<Integer> values) {
        
        Integer result1 = values.get(0);
        Integer result2, result3;
        
        outer:
            for (int i = 0; i < 4; i++) {

                solutionOperators.clear();
                result1 = parseOperations(result1, i, values.get(1), solutionOperators);
                for (int j = 0; j < 4; j++) {
                    
                    result2 = parseOperations(result1, j, values.get(2), solutionOperators);
                    for (int k = 0; k < 4; k++) {
                        
                        result3 = parseOperations(result2, k, values.get(3), solutionOperators);
                        if (result3 == 24)
                            break outer;
                        
                        solutionOperators.remove(2);
                        
                    }
                    solutionOperators.remove(1);
                    
                }

            }
        
        if (solutionOperators.size() == 1)
            solutionText.setText("No solution found");
        else 
            outputSolution(values, solutionOperators);
        
    }
    
    public Integer parseOperations(Integer total, int index, Integer operand, ArrayList<String> operators) {
     
        switch (index) {
            
            case 0:
                total *= operand;
                operators.add("*");
                break;
            case 1:
                total /= operand;
                operators.add("/");
                break;
            case 2:
                total += operand;
                operators.add("+");
                break;
            case 3:
                total -=operand;
                operators.add("-");
                break;
            
        }
        
        return total;
        
    }
    
    public void outputSolution(ArrayList<Integer> values, ArrayList<String> operators) {
        
        solutionText.clear();
        solutionText.setText("(((" + values.get(0) + " " + operators.get(0) + " "
                + values.get(1) + ")" + " " + operators.get(1) + " " 
                + values.get(2) + ")" + " " + operators.get(2) + " " 
                + values.get(3) + ")");
        
    }
        
}
