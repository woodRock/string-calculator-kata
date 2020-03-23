import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {

    private StringCalculator tester = new StringCalculator();

    @Test
    public void emptyString() throws Exception {
        assertEquals(0, tester.Add(""), "Add cannot take an empty argument.");
    }

    @Test
    public void singleNumber() throws Exception {
        assertEquals(1, tester.Add("1"),"The output for add should be one.");
    }

    @Test
    public void twoNumbers() throws Exception {
        assertEquals(3, tester.Add("1,2"),"The output should be 3 for the inputs 1 and 2. ");
    }

    @Test
    public void nNumbers() throws Exception {
        assertEquals(4, tester.Add("1,1,1,1"), "The output for N=4 numbers, should be 4.");
    }

    @Test
    public void newLine() throws Exception {
        assertEquals(6,tester.Add("1\n2,3"),"Numbers can be separated by newline");
    }

    @Test
    public void addWithNewDelimiter() throws Exception {
        assertEquals(3, tester.Add("//;\n1;2"), "The delimiter can be changed.");
    }

    @Test
    public void negativeNumbers() {
        try {
            tester.Add("-1,2");
            fail("An exception should be thrown when negative numbers are used. ");
        } catch (Exception e) {
            assertEquals("negatives are not allowed: -1", e.getMessage(), "Negatives inputs should throw an error.");
        }
    }

    @Test
    public void multipleNegativeNumbers(){
        try {
            tester.Add("-1,-1");
            fail("Multiple negative numbers should throw an error.");
        } catch (Exception e) {
            assertEquals("negatives are not allowed: -1,-1", e.getMessage(), "Multiple negative numbers cannot be added.");
        }
    }

    @Test
    public void getCallCount(){
        assertEquals(0, tester.getCallCount(), "The call count should start off at zero.");
    }

    @Test
    public void callCountIncreases() throws Exception {
        getCallCount();
        twoNumbers();
        assertEquals(1, tester.getCallCount(), "The call count should increase when called.");
    }

    @Test
    public void callCountWithException() throws Exception {
        getCallCount();
        negativeNumbers();
        assertEquals(0, tester.getCallCount(), "Call count shouldn't increase if an exception is thrown.");
    }

    @Test
    public void largeNumbersIgnored() throws Exception {
        assertEquals(1002, tester.Add("2,1000"),"Numbers less than or equal to 1000 are added.");
        assertEquals(2, tester.Add("2,1001"), "Numbers larger than 1001 should be ignored.");
    }

    @Test
    public void delimitersCanBeOfAnyLength() throws Exception {
        assertEquals(6,tester.Add("//[***]\n1***2***3"),"Delimiters of any length.");
    }

    @Test
    public void multipleDelimiters() throws Exception {
        assertEquals(6, tester.Add("//[*][%]\n1*2%3"), "Multiple delimiters can be accepted");
    }

    @Test
    public void multipleDelimitersOfNLength() throws Exception {
        assertEquals(6,tester.Add("//[**][%%]\n1**2%%3"),"Multiple delimiters can each have varying length.");
    }
}