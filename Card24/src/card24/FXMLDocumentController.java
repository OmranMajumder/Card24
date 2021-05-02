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
import javafx.animation.AnimationTimer;
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
    
    // declares and instantiates array lists for card, card codes, valid values, 
    // and solution operators
    ArrayList<Rectangle> cards = new ArrayList();
    ArrayList<String> cardCodes = new ArrayList();
    ArrayList<Double> validValues = new ArrayList();
    ArrayList<String> solutionOperators = new ArrayList();
   
    private int seconds, minutes = 0, hours = 0;
    
    AnimationTimer timer = new AnimationTimer() {
            
        private long lastTime = 0;

        @Override
        public void handle(long now) {

            if (lastTime != 0) {

                if (now > lastTime + 1000000000) {

                    seconds++;
                    if (seconds == 60) {
                        
                        minutes++;
                        seconds = 0;
                        
                        if (minutes == 60) {
                            
                            hours++;
                            minutes = 0;
                            
                        }
                        
                    }
                        
                    timerLabel.setText(String.format("Timer: %02d:%02d:%02d", 
                        hours, minutes, seconds));
                    lastTime = now;

                }

            }

            else
                lastTime = now;

        }

        @Override
        public void stop() {

            super.stop();
            lastTime = 0;
            seconds = 0;

        }
            
    };
    
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
    @FXML
    private Label timerLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initializeCards();
        timer.start();
        drawCards(cardCodes);
        test = removeSpaces(test);
        
        System.out.println(test);
        System.out.println(evaluateExpression(test));
        
    }    
    
    // adds card rectangle objects to array list of rectangles
    private void initializeCards() {
        
        cards.add(cardBox1);
        cards.add(cardBox2);
        cards.add(cardBox3);
        cards.add(cardBox4);
        
    }
    
    // removes spaces from input string
    private String removeSpaces(String input) {
        
        // returns input string with spaces replaced with empty string
        return input.replaceAll(" ", "");
        
    }
    
    // validates correct use of parentheses
    private Boolean validateParentheses(String input) {
        
        // declares and initializes counter variables for left and right
        // parentheses
        int rightCount = 0, leftCount = 0;
        
        // iterates through string counting left and right parenthesese
        for (int i = 0; i < input.length() - 1; i++) {
            
            // increments left and right parentheses counters if parentheses
            // are detected
            if (input.charAt(i) == '(') 
                leftCount++;
            
            else if (input.charAt(i) == ')')
                rightCount++;
            
        }
        
        // returns boolean if every left parenthesis has a matching right
        // parenthesis
        if (rightCount == leftCount)           
            return true;
        
        else 
            return false;
            
    }
    
    // returns position of last left parenthesis
    private int findLastLeftParen(String input) {
        
        // declares counter variable
        int i;
        
        // iterates through string backwards looking for last left parenthesis
        for (i = input.length() - 1; i >= 0; i--) {
            
            // breaks out of loop when the last left parenthesis is found
            if (input.charAt(i) == '(')
                break;
            
        }
        
        // returns position of last left parenthesis
        return i;
        
    }
    
    // returns position of first right parenthesis
    private int findFirstRightParen(String input) {
        
        // declares counter variable
        int i;
        
        // iterates through string backwards looking for last left parenthesis
        for (i = input.length() - 1; i >= 0; i--) {
            
            // breaks out of loop when the last left parenthesis is found
            if (input.charAt(i) == ')')
                break;
            
        }
        
        // returns position of last left parenthesis
        return i;

    }
    
    // evaluates parenthetical expression
    private String evaluateParen(String input) {
        
        // declares variables to identify and hold parenthetical expression
        int i, j;
        String tempString;
        
        // initializes position variables with parentheses positions
        i = findLastLeftParen(input);
        j = findFirstRightParen(input);
        
        // initializes temporary string with parenthetical expression substring
        tempString = input.substring(i + 1, j);
        
        // evaluates parenthetical expression
        tempString = evaluateExpression(tempString);
        
        // refactors input string with evaluated parenthetical expression
        input = (input.substring(0, i)).concat(tempString).concat(input.substring(j + 1));
        
        // returns input string
        return input;
        
    }
    
    // evaluates expression
    private String evaluateExpression(String input) {
        
        // determines whether expression requires parenthetical evaluation
        if (input.contains("(")) {
            
            // continues to evaluate parenthetical expressions if detected
            input = evaluateParen(input);
            input = evaluateExpression(input);
            
        }
        
        else {
            
            // declares and instantiates array lists for operands and operators
            ArrayList<String> operands = new ArrayList<String>();
            ArrayList<String> operators = new ArrayList<String>();
        
            while (input.contains("*") || input.contains("/") 
                    || input.contains("+") || input.contains("-")) {

                sortOperatorsOperands(input, operators, operands);
                input = solveArithmetic(operators, operands);

            }
            // VALIDATE DOUBLE
        }
        
        return input;
         
    }
    
    // sorts operators and operands into array lists
    private void sortOperatorsOperands(String input, ArrayList<String> operators, ArrayList<String> operands) {
        
        // iterates through input string and adds operators and operands to
        // respective array lists
        for (int i = 0; i < input.length(); i++) {
            
            // checks for multiple digit and decimal operands
            if (i > 0 && (Character.isDigit(input.charAt(i))
                    || input.charAt(i) == '.')
                    && (Character.isDigit(input.charAt(i - 1))
                    || input.charAt(i - 1) == '.')) {
                
                // appends current character to previous operand
                operands.set(operands.size() - 1, operands.get(operands.size() - 1) 
                    + input.charAt(i));

            }
            
            // adds singular digit or first digit to operands
            else if (Character.isDigit(input.charAt(i))) 
                operands.add(Character.toString(input.charAt(i)));
            
            // adds operator to array list
            else 
                operators.add(Character.toString(input.charAt(i)));
            
        }
        
    }
    
    // solves arithmetic expressions and returns result as string
    private String solveArithmetic(ArrayList<String> operators, ArrayList<String> operands) {
        
        // declares and initializes variable for arithmetic result
        Double result = 0.0;
        
        // iterates through array list of operators
        for (int i = 0; i < operators.size(); i++) {
            
            // checks for multiplication or division operations
            if (operators.get(i).equals("*") || operators.get(i).equals("/")) {
                
                // calculates product or quotient
                if (operators.get(i).equals("*"))
                    result = Double.parseDouble(operands.get(i)) * Double.parseDouble(operands.get(i + 1));

                else
                    result = Double.parseDouble(operands.get(i)) / Double.parseDouble(operands.get(i + 1));
                
                // replaces operand in array list with result
                operands.set(i + 1, result.toString());

            }
            
            // checks for addition or subtraction operations
            if (operators.get(i).equals("+") || operators.get(i).equals("-")) {
                
                // calculates sum or difference
                if (operators.get(i).equals("+"))
                    result = Double.parseDouble(operands.get(i)) + Double.parseDouble(operands.get(i + 1));

                else
                    result = Double.parseDouble(operands.get(i)) - Double.parseDouble(operands.get(i + 1));
                
                // replaces operand in array list with result
                operands.set(i + 1, result.toString());

            }

        }
        
        // returns arithmetic result as string
        return result.toString();
        
    }
    
    public void drawCards(ArrayList<String> cardCodes) {
        
        // declares variables for suit, card value, and combined string card code
        Integer suit, cardValue;
        String cardCode;
        
        // declares and instantiates random number generator
        Random randGen = new Random();
        
        // iterates through 4 draws
        for (int i = 0; i < 4; i++) {
            
            // randomly generates suit and card value
            suit = randGen.nextInt(4) + 1;
            cardValue = randGen.nextInt(13) + 1;
            
            // concatenates suit and card value into card code
            cardCode = Integer.toString(suit) + Integer.toString(cardValue);
            
            // adds card drawn if it is not a duplicate draw
            if (i == 0)
                cardCodes.add(cardCode);
            
            else if (i != 0 && !(cardCodes.contains(cardCode)))
                cardCodes.add(cardCode);
            
            else
                i--;

        }
        
        // sets card images to rectangle objects
        setCardFaces(cardCodes);
        
    }
    
    // stores card values in array list of valid values
    private void storeCardValues(ArrayList<String> cardsDrawn) {
        
        // iterates through cards drawn
        for (int i = 0; i < cardsDrawn.size(); i++) {
            
            // stores double value in array list of valid values
            validValues.add(Double.parseDouble(cardsDrawn.get(i).substring(1)));
            
        }

    }    
    
    // sets card image backgrounds of rectangle objects
    private void setCardFaces(ArrayList<String> cardsDrawn) {
        
        // iterates through array list of cards drawn
        for (int i = 0; i < cardsDrawn.size(); i++) {
            
            // sets card image as image pattern of rectangle
            Image img = new Image("file:PlayingCards/" + cardsDrawn.get(i) + ".png");
            cards.get(i).setFill(new ImagePattern(img));
            
        }
        
        // stores card values in array list of valid values
        storeCardValues(cardsDrawn);
        
    }
    
    // resets array lists and text fields when game is ended
    private void endGame() {
        
        // Add to user game count and user results count
        cardCodes.clear();
        validValues.clear();
        solutionText.clear();
        userText.clear();
        // Run random number generator again
        
    }
    
    // generates possible solution for set of card values
    public void solutionFinder(ArrayList<Double> values) {
        
        // declares variables for intermediate calculations
        Double result0 = values.get(0);
        Double result1, result2, result3;
        
        // labels outer loop
        outer:
        
            // iterates through arithmetic operations and calculates intermediate
            // results
            for (int i = 0; i < 4; i++) {

                solutionOperators.clear();
                result1 = parseOperations(result0, i, values.get(1), solutionOperators);
                
                for (int j = 0; j < 4; j++) {
                    
                    result2 = parseOperations(result1, j, values.get(2), solutionOperators);
                    
                    for (int k = 0; k < 4; k++) {
                        
                        result3 = parseOperations(result2, k, values.get(3), solutionOperators);
                        
                        // breaks out of outer loop if solution is found
                        if (Math.abs(result3 - 24.0) < 0.00001)
                            break outer;
                        
                        solutionOperators.remove(2);
                        
                    }
                    
                    solutionOperators.remove(1);
                    
                }

            }
        
        // outputs message if no solution is found
        if (solutionOperators.size() == 1)
            solutionText.setText("No solution found");
        
        // outputs formatted solution
        else 
            outputSolution(values, solutionOperators);
        
    }
    
    // evaluates solution finder arithmetic operations
    public Double parseOperations(Double total, int index, Double operand, ArrayList<String> operators) {
        
        // determines arithmetic operation
        switch (index) {
            
            // calculates arithmetic result
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
                total -= operand;
                operators.add("-");
                break;
            
        }
        
        // returns arithmetic result
        return total;
        
    }
    
    // outputs formatted solution to solution text field
    public void outputSolution(ArrayList<Double> values, ArrayList<String> operators) {
        
        // clears existing solution text and outputs solution if found
        solutionText.clear();
        solutionText.setText(String.format("((%.0f %s %.0f) %s %.0f) %s %.0f", values.get(0), 
                operators.get(0), values.get(1), operators.get(1), values.get(2),
                operators.get(2), values.get(3))); 

    }
    
    // imported button method for finding solution
    @FXML
    private void findSolution(ActionEvent event) {
        
        solutionFinder(validValues);
        
    }
    
    // imported button method to refresh cards
    @FXML
    private void redrawCards(ActionEvent event) {
        
        endGame();
        drawCards(cardCodes);
        
    }
    
    // imported button method to verify expression
    @FXML
    private void verifyExpression(ActionEvent event) {
        
        evaluateExpression(userText.getText());
        
    }
    
}
