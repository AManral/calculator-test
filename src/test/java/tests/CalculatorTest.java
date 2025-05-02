package tests;

import base.BaseTest;
import constants.CalculatorConstants;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

public class CalculatorTest extends BaseTest {

    static Logger log = Logger.getLogger(CalculatorTest.class.getName());

    //Datasets including calculator operation scenarios
    @DataProvider(name = "basicOperations")
    public Object[][] basicOperations() {
        return new Object[][]{
                {"Addition Test", "2+3=", "5"},
                {"Subtraction Test", "10-4=", "6"},
                {"Multiplication Test", "6×7=", "42"},
                {"Division Test", "9÷3=", "3"},
                {"Decimal Addition Test", "1.5+2.5=", "4"},
                {"Negative value Test", "1.5-2.5=", "-1"},
                {"Divide by Zero Test", "8÷0=", "Infinity"}
        };
    }

    @DataProvider(name = "complexOperations")
    public Object[][] complexOperations() {
        return new Object[][]{
                {"Multiple operations Test", "2+3*7+5+78=", "106"},
                {"Priority order Test: Check BODMAS Rule", "10+67*31-42/12=", "2083.5"},
                {"Chain Operations Test: Continuous Operation on result", "6×7=+89=-32=*2=", "198"},
                {"Large calculations Test", "99999*99999*99999=", "9.9997e+14"},
                {"Big Decimal Operations Test", "1.524354637*2.974567346724=", "4.53429553"},
                {"Leading Zeros Test", "00001.8+4=", "5.8"}
        };
    }

    @DataProvider(name = "operatorHandling")
    public Object[][] operatorHandling() {
        return new Object[][]{
                {"Operator override using '-' after '+'", "7+-5", "7 - 5"},
                {"Operator override using '*' or '/' after '+'", "9+/*2", "9 × 2"},
                {"Operator override using after '-'", "9-/*+1", "9 + 1"},
                {"Operator override using '+' after '*' or '/'", "4*+/+2", "4 + 2"},
                {"Operator override using '-' after '*'", "3*-4", "3 × -4"},
                {"Operator override using '-' after '/'", "3/-1", "3 ÷ -1"},
                {"No override when using '+' after '*-'", "6*-+1", "6 × -1"},
                {"No override when using '+' after '/-'", "6/-+1", "6 ÷ -1"},
                {"Equal after any operator", "14+=", "14 + "},
                {"Repeated equals", "2+2===", "4"},
                {"Operator at start", "/9", "0 ÷ 9"},
                {"Repeated decimal", "12...001=", "12.001"},
                {"Only decimal after operator", "1+.=", "Error"}
        };
    }

    //End to End test scenarios
    @Test(dataProvider = "basicOperations", groups = {"sanity", "regression"}, description = "Validate basic calculator functions")
    public void testBasicOperations(String testName, String input, String expected) {
        log.info("Running test: " + testName);

        calculatorPage.enterOperations(input);

        Assert.assertEquals(calculatorPage.getCurrentResult(),expected,"Calculated result: "+calculatorPage.getCurrentResult()+" not as expected "+expected);
        }

    @Test(dataProvider = "complexOperations", groups = {"regression"}, description = "Validate complex calculator operations")
    public void testComplexOperations(String testName, String input, String expected) {
        log.info("Running test: " + testName);

        calculatorPage.enterOperations(input);
        String actualResult = calculatorPage.getCurrentResult();

        System.out.println("Actual Result: "+actualResult);
        System.out.println("Expected Result: "+expected);


        if(testName.equals("Big Decimal Operations Test")) {
            BigDecimal difference=(new BigDecimal(expected)).subtract(new BigDecimal(actualResult));
            BigDecimal tolerance = new BigDecimal("0.0001");

            Assert.assertTrue(difference.abs().compareTo(tolerance) <= 0);
        }else {
            Assert.assertEquals(calculatorPage.getCurrentResult(), expected, "Calculated result: "+calculatorPage.getCurrentResult()+" not as expected "+expected);
        }

    }

    @Test(dataProvider = "operatorHandling",groups = {"regression"}, description = "Validate edge case scenarios around operators")
    public void testOperatorHandling(String testName, String input, String expected) {
        log.info("Running test: " + testName);

        if(!calculatorPage.getCurrentResult().equals("0") && !calculatorPage.isAllClearButtonVisible()){
            calculatorPage.clearAllEntries();
        }else calculatorPage.clickAllClearButton();

        calculatorPage.enterOperations(input);
        String actualResult = calculatorPage.getCurrentResult();

        log.info("Actual Result: "+actualResult);
        log.info("Expected Result: "+expected);

        Assert.assertEquals(actualResult,expected,"Calculated result: "+actualResult+" not as expected "+expected);

    }

    @Test(groups = {"regression"}, description = "Validate clear entry[CE] and clear all[AC] functionalities")
    public void clearFunctionalityTest() throws InterruptedException {
        char[] inputs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '/', '*','.'};
        Random random = new Random();
        char randomChar = inputs[random.nextInt(inputs.length)];
        
        log.info("Verify default state");
        Assert.assertTrue(calculatorPage.isAllClearButtonVisible());
        Assert.assertFalse(calculatorPage.isClearEntryButtonVisible());
        
        log.info("Verify once input is added");
        calculatorPage.enterOperations(String.valueOf(randomChar));

        Assert.assertFalse(calculatorPage.isAllClearButtonVisible(), "AC button state is not as expected");
        Assert.assertTrue(calculatorPage.isClearEntryButtonVisible(), "CE button state is not as expected");

        log.info("Verify when input cleared");
        calculatorPage.clickClearEntryButton();

        Assert.assertEquals("0",calculatorPage.getCurrentResult());
        Assert.assertTrue(calculatorPage.isClearEntryButtonVisible(), "CE button state is not as expected");
        
        log.info("Verify CE only deletes last input");
        calculatorPage.enterOperations("7+12");
        calculatorPage.clickClearEntryButton();
        
        Assert.assertEquals("7 + 1",calculatorPage.getCurrentResult());

        log.info("Verify AC enables only after '=' is clicked in an operation");
        calculatorPage.enterOperations("=");

        Assert.assertTrue(calculatorPage.isAllClearButtonVisible(),"AC button state is not as expected");
        Assert.assertFalse(calculatorPage.isClearEntryButtonVisible(),"CE button state is not as expected");

        log.info("Verify AC and CE in chained operations");
        calculatorPage.enterOperations("+62=");
        
        Assert.assertTrue(calculatorPage.isAllClearButtonVisible(),"AC button state is not as expected");
        Assert.assertFalse(calculatorPage.isClearEntryButtonVisible(),"CE button state is not as expected");
        
        log.info("Validate behavior when AC is clicked");
        calculatorPage.clickAllClearButton();
        
        Assert.assertEquals("0",calculatorPage.getCurrentResult());

    }

    @Test(groups = {"sanity","regression"}, description = "Validate all UI/UX features of calculator")
    public void defaultStateTest() {

        calculatorPage.clickGoogleSearchButton(); //to load default page
        char[] numberInputs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','.'};
        char[] operatorInputs = {'+', '-', '/', '*'};
        
        log.info("Verify default state of calculator results screen");
        Assert.assertEquals(calculatorPage.getCurrentResult(),"0","Default result is not as expected");
        Assert.assertEquals(calculatorPage.getAnswerText(),"  ","Default Answer is not as expected");

        log.info("Verify all number buttons are clickable");
        for (char c:numberInputs) {
            calculatorPage.enterOperations(String.valueOf(c));
            Assert.assertEquals(calculatorPage.getCurrentResult(),String.valueOf(c), "Result is not as expected");
            Assert.assertEquals( calculatorPage.getAnswerText(), CalculatorConstants.DEFAULT_CAL_ANSWER, "Answer is not as expected");
            calculatorPage.clickClearEntryButton();
        }

        log.info("Verify all operator buttons are clickable");
        for (char c:operatorInputs) {
            calculatorPage.enterOperations(String.valueOf(c));
            if(c=='/'){
                Assert.assertEquals(calculatorPage.getCurrentResult(),"0 ÷ ", "Result is not as expected");
            } else if (c=='*') {
                Assert.assertEquals(calculatorPage.getCurrentResult(),"0 × ", "Result is not as expected");
            }else Assert.assertEquals(calculatorPage.getCurrentResult(),"0 "+String.valueOf(c)+" ", "Result is not as expected");

            Assert.assertEquals(calculatorPage.getAnswerText(), CalculatorConstants.DEFAULT_CAL_ANSWER, "Answer is not as expected");
            calculatorPage.clickClearEntryButton();

        log.info("Verify '=' clicked with no input");
         calculatorPage.enterOperations("=");

         Assert.assertEquals("0",calculatorPage.getCurrentResult(), "Result is not as expected");
         Assert.assertEquals(calculatorPage.getAnswerText(),"0 =","Answer is not as expected");
        }
    }

}
