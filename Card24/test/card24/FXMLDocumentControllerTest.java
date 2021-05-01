/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card24;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author clofaso
 */
public class FXMLDocumentControllerTest {
    
    FXMLDocumentController instance = new FXMLDocumentController();
    
    public FXMLDocumentControllerTest() {
    }

    @Test
    public void testInitialize() {
    }

    @Test
    public void testRemoveSpaces() {
        System.out.println("Remove Spaces:");
        String testInput = "1";
        String expResult = "1";
        String result = instance.removeSpaces(testInput);
        assertEquals(expResult, result);
        //Control: No spaces included
    }
    @Test
    public void testRemoveSpaces2() {
        System.out.println("Remove Spaces 2:");
        String testInput = "1 + 3 ";
        String expResult = "1+3";
        String result = instance.removeSpaces(testInput);
        assertEquals(expResult, result);
    }
    @Test
    public void testRemoveSpaces3() {
        System.out.println("Remove Spaces 3:");
        String testInput = "Group 3 Playing Card 24 Project";
        String expResult = "Group3PlayingCard24Project";
        String result = instance.removeSpaces(testInput);
        assertEquals(expResult, result);
    }
    @Test
    public void testRemoveSpaces4() {
        System.out.println("Remove Spaces 4:");
        String testInput = "( 1 + 3 )* c";
        String expResult = "(1+3)*c";
        String result = instance.removeSpaces(testInput);
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateParentheses() {
        System.out.println("Validate Parentheses:");
        String testInput = "1";
        Boolean expResult = true;
        Boolean result = instance.validateParentheses(testInput);
        assertEquals(expResult, result);
        //Control: No () included
    }
    @Test
    public void testValidateParentheses2() {
        System.out.println("Validate Parentheses 2:");
        String testInput = "(1)";
        Boolean expResult = true;
        Boolean result = instance.validateParentheses(testInput);
        assertEquals(expResult, result);
        //Control: Single set of () included with single digit
    }
    @Test
    public void testValidateParentheses3() {
        System.out.println("Validate Parentheses 3:");
        String testInput = "(13+9)+8";
        Boolean expResult = true;
        Boolean result = instance.validateParentheses(testInput);
        assertEquals(expResult, result);
    }
    @Test
    public void testValidateParentheses4() {
        System.out.println("Validate Parentheses 4:");
        String testInput = "(13+(9)+8";
        Boolean expResult = true;
        Boolean result = instance.validateParentheses(testInput);
        assertEquals(expResult, result);
        //Planned failure: uneven number of () - 3 included
    }
    @Test
    public void testValidateParentheses5() {
        System.out.println("Validate Parentheses 5:");
        String testInput = "((13+9)+8)";
        Boolean expResult = true;
        Boolean result = instance.validateParentheses(testInput);
        assertEquals(expResult, result);
    }
    @Test
    public void testValidateParentheses6() {
        System.out.println("Validate Parentheses 6:");
        String testInput = "((13+9)+8*(c))";
        Boolean expResult = true;
        Boolean result = instance.validateParentheses(testInput);
        assertEquals(expResult, result);
    }
    
//    **UNEXPECTED FAILURES FOR testValidateParentheses2, 5, and 6
//    ***REQUIRES FURTHER INVESTIGATION***

    @Test
    public void testFindLastLeftParen() {
    }

    @Test
    public void testFindFirstRightParen() {
    }

    @Test
    public void testEvaluateParen() {
    }

    @Test
    public void testEvaluateExpression() {
    }

    @Test
    public void testSolveArithmetic() {
    }

    @Test
    public void testSortOperatorsOperands() {
    }

    @Test
    public void testSetCardFaces() {
    }

    @Test
    public void testInitializeCards() {
    }

    @Test
    public void testStoreCardValues() {
    }

    @Test
    public void testEndGame() {
    }

    @Test
    public void testDrawCards() {
    }

    @Test
    public void testSolutionFinder() {
    }

    @Test
    public void testParseOperations() {
    }

    @Test
    public void testOutputSolution() {
    }
    
}
