/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card24;

import card24.FXMLDocumentController.Game;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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

public class FXMLDocumentController implements Initializable {
    
    // declares and instantiates array lists for card, card codes, valid values, 
    // and solution operators
    ArrayList<Rectangle> cards = new ArrayList();
    ArrayList<String> cardCodes = new ArrayList();
    ArrayList<Double> validValues = new ArrayList();
    ArrayList<Double> inputValues = new ArrayList();
    ArrayList<String> solutionOperators = new ArrayList();
    ArrayList<Game> games = new ArrayList();
   
    private int seconds;
    private int gameCount = 0;
    String gameResult = "";
    
    // declares and instantiates animation timer for background-running timer
    AnimationTimer timer = new AnimationTimer() {
        
        // declares and initializes current time
        private long lastTime = 0;
        
        // defines abstract method to start timer
        @Override
        public void handle(long now) {
            
            if (lastTime != 0) {
                
                // enters condition if difference between now and last time
                // is greater than 1 second
                if (now > lastTime + 1000000000) {
                    
                    // increments seconds
                    seconds++;
                    
                    // outputs formatted timer string
                    timerLabel.setText(String.format("%02d:%02d:%02d", 
                        seconds / 3600, seconds / 60, seconds % 60));
                    lastTime = now;

                }

            }
            
            else
                lastTime = now;

        }
        
        // defines abstract method to stop timer
        @Override
        public void stop() {
            
            super.stop();
            lastTime = 0;
            seconds = 0;

        }
            
    };
    
    protected class Game {
        
        int time;
        ArrayList<String> attempts;
        String result;
        
        Game() {
            
            time = 0;
            attempts = new ArrayList();
            result = "";
            
        }
        
    }
    
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
    @FXML
    private Rectangle timerBox;
    @FXML
    private Button saveLogButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initializeCards();
        initializeTextFields();
        timer.start();
        drawCards(cardCodes);
        games.add(new Game());
        
    }    
    
    // adds card rectangle objects to array list of rectangles
    protected void initializeCards() {
        
        cards.add(cardBox1);
        cards.add(cardBox2);
        cards.add(cardBox3);
        cards.add(cardBox4);
        
    }
    
    // removes spaces from input string
    protected String removeSpaces(String input) {
        
        // returns input string with spaces replaced with empty string
        return input.replaceAll(" ", "");
        
    }
    
    // validates correct use of parentheses
    protected Boolean validateParentheses(String input) {
        
        // declares and initializes counter variables for left and right
        // parentheses
        int rightCount = 0, leftCount = 0;
        
        // iterates through string counting left and right parenthesese
        for (int i = 0; i < input.length(); i++) {
            
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
    protected int findLastLeftParen(String input) {
        
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
    protected int findFirstRightParen(String input) {
        
        // declares counter variable
        int i;
        
        // iterates through string backwards looking for first right parenthesis
        for (i = 0; i < input.length(); i++) {
            
            // breaks out of loop when the last right parenthesis is found
            if (input.charAt(i) == ')')
                break;
            
        }
        
        // returns position of last right parenthesis
        return i;

    }
    
    // returns position of first left parenthesis for case (a $ b) $ (c $ d)
    protected int findFirstLeftParen(String input) {
        
        // declares counter variable
        int i;
        
        // iterates through string backwards looking for first left parenthesis
        for (i = 0; i < input.length() - 1; i++) {
            
            // breaks out of loop when the last left parenthesis is found
            if (input.charAt(i) == '(')
                break;
            
        }
        
        // returns position of last left parenthesis
        return i;
    }
    
    // evaluates parenthetical expression
    protected String evaluateParen(String input) {
        
        // declares variables to identify and hold parenthetical expression
        int i, j;
        String tempString;
        
        // initializes position variables with parentheses positions
        i = findLastLeftParen(input);
        j = findFirstRightParen(input);
        
        if (i > j)
            i = findFirstLeftParen(input);
        
        // initializes temporary string with parenthetical expression substring
        tempString = input.substring(i + 1, j);
        
        // evaluates parenthetical expression
        tempString = evaluateExpression(tempString);
        
        // refactors input string with evaluated parenthetical expression
        input = (input.substring(0, i)).concat(tempString).concat(input.substring(j + 1));
        
        // returns input string
        return input;
        
    }
    
    // evaluates user input expression
    protected String evaluateExpression(String input) throws IllegalArgumentException {
        
        // removes spaces from input
        input = removeSpaces(input);
        
        try {
        // determines whether expression requires parenthetical evaluation
            if (input.contains("(")) {

                // continues to evaluate parenthetical expressions if detected
                input = evaluateParen(input);
                input = evaluateExpression(input);

            }

            else if (input.contains("*") || input.contains("/")
                    || input.contains("+") || input.contains("-")) {

                // declares and instantiates array lists for operands and operators
                ArrayList<String> operands = new ArrayList<>();
                ArrayList<String> operators = new ArrayList<>();

                while (input.contains("*") || input.contains("/") 
                        || input.contains("+") || input.contains("-")) {
                        
                    sortOperatorsOperands(input, operators, operands);
                    
                    if (operands.get(operands.size() - 1).equals(operands.get(operands.size() - 2))) {
                        
                        break;
                        
                    }
                    
                    else
                        input = solveArithmetic(operators, operands);

                }

            }
            
        }
        
        catch (IllegalArgumentException e) {
            
            solutionText.setText("Invalid Entry! Try Again.");
            
        }
        
        return input;
        
    }
    
    // sorts operators and operands into array lists
    protected void sortOperatorsOperands(String input, ArrayList<String> operators, ArrayList<String> operands) {
        
        // iterates through input string and adds operators and operands to
        // respective array lists
        for (int i = 0; i < input.length(); i++) {
            
            if (i == 0 && input.charAt(i) == '-') {
                
                operands.add(Character.toString(input.charAt(i)));
                
            }
            
            else if (i > 0 && input.charAt(i) 
                    == '-') {
                
                if (input.charAt(i - 1) == '*' || input.charAt(i - 1) == '/' 
                        || input.charAt(i - 1) == '+' || input.charAt(i - 1) == '-')     
                    operands.add(Character.toString(input.charAt(i)));
                
                else
                    operators.add(Character.toString(input.charAt(i)));
                
            }
            
            // checks for multiple digit and decimal operands
            else if (i > 0 && (Character.isDigit(input.charAt(i))
                    || input.charAt(i) == '.')
                    && (Character.isDigit(input.charAt(i - 1))
                    || input.charAt(i - 1) == '.' 
                    || operands.get(operands.size() - 1).equals("-"))) {
                
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
    protected String solveArithmetic(ArrayList<String> operators, ArrayList<String> operands) {
        
        // declares and initializes variable for arithmetic result
        Double result = 0.0;
        
        // iterates through array list of operators
        for (int i = 0; i < operators.size(); i++) {
            
            // checks for multiplication or division operations
            if (operators.get(i).equals("*") || operators.get(i).equals("/")) {
                
                // calculates product or quotient
                if (operators.get(i).equals("*"))
                    result = Double.parseDouble(operands.get(i)) 
                            * Double.parseDouble(operands.get(i + 1));

                else
                    result = Double.parseDouble(operands.get(i)) 
                            / Double.parseDouble(operands.get(i + 1));
                
                // replaces operand in array list with result
                operands.set(i + 1, result.toString());

            }
            
            // checks for addition or subtraction operations
            if (operators.get(i).equals("+") || operators.get(i).equals("-")) {
                
                // calculates sum or difference
                if (operators.get(i).equals("+"))
                    result = Double.parseDouble(operands.get(i)) 
                            + Double.parseDouble(operands.get(i + 1));

                else
                    result = Double.parseDouble(operands.get(i)) 
                            - Double.parseDouble(operands.get(i + 1));
                
                // replaces operand in array list with result
                operands.set(i + 1, result.toString());

            }

        }
        
        // returns arithmetic result as string
        return result.toString();
        
    }
    
    protected void drawCards(ArrayList<String> cardCodes) {
        
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
    protected void storeCardValues(ArrayList<String> cardsDrawn) {
        
        // iterates through cards drawn
        for (int i = 0; i < cardsDrawn.size(); i++) {
            
            // stores double value in array list of valid values
            validValues.add(Double.parseDouble(cardsDrawn.get(i).substring(1)));
            
        }
        
        Collections.sort(validValues);

    }    
    
    // sets card image backgrounds of rectangle objects
    protected void setCardFaces(ArrayList<String> cardsDrawn) {
        
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
    protected void endGame() {
        
        // sets game time to current time elapsed
        games.get(gameCount).time = seconds;
        games.get(gameCount).result = gameResult;
        
        // increment game counter
        gameCount++;
        
        // reset values
        gameResult = "";
        cardCodes.clear();
        validValues.clear();
        inputValues.clear();
        solutionText.clear();
        userText.clear();
        
        // restarts timer
        timerLabel.setText("00:00:00");
        timer.stop();
        timer.start();
        
        // starts new game
        games.add(new Game());
        
    }
    
    // generates possible solution for set of card values
    protected void solutionFinder(ArrayList<Double> values) {
        
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
        if (solutionOperators.size() == 1) {
           
            solutionText.setStyle("-fx-text-inner-color: black");
            solutionText.setText("No solution found");
            
            // set game result to no solution
            gameResult = "No Solution";
            
        }
        
        // outputs formatted solution
        else  {
            
            outputSolution(values, solutionOperators);
            
            if (gameResult.equals(""))
                gameResult = "Loss";
            
        }
        
    }
    
    // evaluates solution finder arithmetic operations
    protected Double parseOperations(Double total, int index, Double operand, ArrayList<String> operators) {
        
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
    protected void outputSolution(ArrayList<Double> values, ArrayList<String> operators) {
        
        // clears existing solution text and outputs solution if found
        solutionText.clear();
        solutionText.setStyle("-fx-text-inner-color: black");
        solutionText.setText(String.format("((%.0f %s %.0f) %s %.0f) %s %.0f", values.get(0), 
                operators.get(0), values.get(1), operators.get(1), values.get(2),
                operators.get(2), values.get(3)));

    }
    
    // stores user input values in array list for comparing with valid values
    private void parseInputValues(String input) {
        
        inputValues.clear();
        removeSpaces(input);
        
        // iterates through input string
        for (int i = 0; i < input.length(); i++) {
            
            // adds first digit to array
            if (i == 0) {
                
                if (Character.isDigit(input.charAt(i))) {
                
                    inputValues.add(Double.parseDouble(Character.toString(input.charAt(i))));
                
                }
                
                else;
            }
            
            else {
                
                // appends digit to previous element
                if (Character.isDigit(input.charAt(i)) 
                        && Character.isDigit(input.charAt(i - 1))) {
                
                    inputValues.set(inputValues.size() - 1, 
                            inputValues.get(inputValues.size() - 1) * 10.0
                                    + Double.parseDouble(Character.toString(input.charAt(i))));
                
                }
                
                // adds single digit to array
                else if (Character.isDigit(input.charAt(i))) {
                        
                    inputValues.add(Double.parseDouble(Character.toString(input.charAt(i))));
                    
                }
            
            }
            
            
            
        }
        
        // sorts array list of input values
        Collections.sort(inputValues);
        
    }
    
    // generates solution if found
    @FXML
    protected void findSolution(ActionEvent event) {
        
        solutionFinder(validValues);
        games.get(gameCount).result = gameResult;
        
    }
    
    // redraws cards starting a new game
    @FXML
    protected void redrawCards(ActionEvent event) {
        
        endGame();
        
        drawCards(cardCodes);
        
    }
    
    // verifies whether expression equals 24
    @FXML
    protected void verifyExpression(ActionEvent event) {
        
        // add user input to array list of attempts
        games.get(gameCount).attempts.add(userText.getText());
        
        try{
            
            parseInputValues(userText.getText());
            
            // checks if user used valid values
            if (validValues.equals(inputValues)) {
            
                String result = evaluateExpression(userText.getText());
                
                if (result.contains("*") || result.contains("/")
                    || result.contains("+") || result.contains("-")) {
                    
                    result = evaluateExpression(result);
                    
                }

                // changes solution text color if correct or incorrect
                if (Math.abs(Double.parseDouble(result) - 24.0) < 0.00001){
                    
                    if (gameResult.equals("") || gameResult.equals("Win")) {
                        
                        gameResult = "Win";
                        solutionText.setStyle("-fx-text-inner-color: green");
                        solutionText.setText("Correct! Expression equals 24!");
                        
                    }
                    
                    else if (gameResult.equals("No Solution")) {
                        
                        solutionText.setStyle("-fx-text-inner-color: black");
                        solutionText.setText("No Solution Exists. Click Refresh!");
                        
                    }
                    
                    else {
                        
                        solutionText.setStyle("-fx-text-inner-color: red");
                        solutionText.setText("You cheated! Click Refresh!");
                        
                    }
                    
                } 

                else{

                    solutionText.setStyle("-fx-text-inner-color: red");
                    solutionText.setText("Try again. Expression not 24.");
                    
                    // set game result to loss
                    gameResult = "Loss";
                    
                }
                
            }
            
            else {
                
                // outputs invalid entry in red for incorrect card values
                solutionText.setStyle("-fx-text-inner-color: red");
                solutionText.setText("Invalid Entry. Incorrect card values!");
                
                // set game result to loss
                gameResult = "Loss";
                
            }
            
        }
        
        catch (IllegalArgumentException e){
            
            // outputs invalid entry message in red
            solutionText.setStyle("-fx-text-inner-color: red");
            solutionText.setText("Invalid Entry. Try again.");
            
        }
    
        
    }
    
    @FXML
    private void saveLog(ActionEvent event) throws IOException {
        
        int wins = 0, losses = 0, noSolutions = 0;
        
        FileWriter fw = new FileWriter("Card24UserLog.txt");
        PrintWriter pw = new PrintWriter(fw);
        
        pw.printf("%-12s%-12s%-12s%-12s%-15s%-15s\n", "Game", "Time", "Wins", "Losses", "No Solution", "Result");
        
        for (int i = 0; i < games.size(); i++) {
            
            if (games.get(i).result.equals("Win"))
                wins++;
            else if (games.get(i).result.equals("Loss"))
                losses++;
            else if (games.get(i).result.equals("No Solution"))
                noSolutions++;
            else
                games.get(i).result = "Incomplete";

            pw.printf("%-12s%02d:%02d:%02d    %-12s%-12s%-15s%-15s\n", i + 1, 
                    games.get(i).time / 3600, games.get(i).time / 60, games.get(i).time % 60,
                    wins, losses, noSolutions, games.get(i).result);
            
            for (int j = 0; j < games.get(i).attempts.size(); j++) {
                
                pw.printf("\t    Attempt #%s: %-25s\n", j + 1, games.get(i).attempts.get(j));
                
            }
            
        }
        
        pw.close();
        fw.close();
        
    }
    
    // initializes format of text fields
    protected void initializeTextFields() {
        
        // ouputs user prompt in solution text field
        solutionText.setText("Valid Characters: 0-9 * / + - ( )");

    }
    
}
