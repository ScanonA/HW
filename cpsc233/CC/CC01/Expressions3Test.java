import static org.junit.Assert.*;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Expressions3Test {
	String filename = "Expressions3.java";

	private boolean containsImportStatement() {
		boolean containsImport = false;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = in.readLine();
			while (line != null && !containsImport) {
				if (line.matches("import+\\s.*")) {
					containsImport = true;
				} 
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return containsImport;
	}
	
	/**
	Checks if the specified library is used anywhere in the code tested.  It checks
	for the word exactly.  If there is a variable name that contains the library name,
	this will result in a false positive.
	*/
	private boolean usesLibrary(String libraryName) {
		boolean usesLibrary = false;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = in.readLine();
			while (line != null && !usesLibrary) {
				if (line.contains(libraryName)) {
					usesLibrary = true;
				} 
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return usesLibrary;	
	}
	
	/**
		Checks if the coding construct is used in the class we're testing.  It expects the 
		construct to be preceded and followed by white space or parenthesis.
	*/
	private boolean usesConstruct(String constructName) {
		boolean usesConstruct = false;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = in.readLine();
			while (line != null && !usesConstruct) {
				if (line.matches(".*\\s+if+[\\s+,(].*")) {
					usesConstruct = true;
				} 
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return usesConstruct;	
	}
		
	@Test
	public void test_isDigit_0() {
		assertFalse("Don't use the Character class in your code.",usesLibrary("Character"));
		
		boolean expected = true;
		boolean actual = Expressions3.isDigit('0');
		
		assertEquals("Checking if 0 is a digit", expected, actual);
	}

	@Test
	public void test_isDigit_1() {
		assertFalse("Don't use the Character class in your code.",usesLibrary("Character"));
		
		boolean expected = true;
		boolean actual = Expressions3.isDigit('1');
		
		assertEquals("Checking if 1 is a digit", expected, actual);
	}

	@Test
	public void test_isDigit_9() {
		assertFalse("Don't use the Character class in your code.",usesLibrary("Character"));
		
		boolean expected = true;
		boolean actual = Expressions3.isDigit('9');
		
		assertEquals("Checking if 9 is a digit", expected, actual);
	}

	@Test
	public void test_isDigit_$() {
		assertFalse("Don't use the Character class in your code.",usesLibrary("Character"));
		
		boolean expected = false;
		boolean actual = Expressions3.isDigit('$');
		
		assertEquals("Checking if # is a digit", expected, actual);
	}

	@Test
	public void test_isDigit_a() {
		assertFalse("Don't use the Character class in your code.",usesLibrary("Character"));
		
		boolean expected = false;
		boolean actual = Expressions3.isDigit('a');
		
		assertEquals("Checking if a is a digit", expected, actual);
	}

	
	@Test
	public void test_computePolynomial_0_0() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = -33.5;
		double actual = Expressions3.computePolynomial(0.0);
		
		assertEquals("Value of polynomial at 0.0", expected, actual, 0.00001);
	}

	@Test
	public void test_computePolynomial_5_717798() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = 0.0;
		double actual = Expressions3.computePolynomial(5.717798);	
		assertEquals("Value of polynomial at 5.717798", expected, actual, 0.000001);
	}

	@Test
	public void test_computePolynomial_negativeNum() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = -11.71875;
		double actual = Expressions3.computePolynomial(-10.25);
		
		assertEquals("Value of polynomial at -10.25", expected, actual, 0.00001);
	}

	@Test
	public void test_computePolynomial_SmallNegativeNum() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = 3841348862.5;
		double actual = Expressions3.computePolynomial(-87654.0);
		
		assertEquals("Value of polynomial at -876.0", expected, actual, 0.00001);
	}

	@Test
	public void test_computePolynomial_LargeNum() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = 48797282.5;
		double actual = Expressions3.computePolynomial(9876.0);
		
		assertEquals("Value of polynomial at 987.0", expected, actual, 0.00001);
	}

	@Test
	public void test_afterDecimal_afterDecimalIsZero() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = 0.0;
		double actual = Expressions3.afterDecimal(15.0);
		
		assertEquals("Value after decimal for 15.0", expected, actual, 0.00001);
	}

	@Test
	public void test_afterDecimal_afterDecimalOfNegativeNum() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = -0.763999999;
		double actual = Expressions3.afterDecimal(-1876.764);
		
		assertEquals("Value after decimal for -1876.764", expected, actual, 0.00001);
	}

	@Test
	public void test_afterDecimal_afterDecimalOfNothinBeforeDecimal() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = 0.123456789;
		double actual = Expressions3.afterDecimal(0.123456789);
		
		assertEquals("Value after decimal for 0.123456789", expected, actual, 0.000000001);
	}

	@Test
	public void test_afterDecimal_afterDecimalTiny() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		
		double expected = 0.000000001;
		double actual = Expressions3.afterDecimal(1.000000001);
		
		assertEquals("Value after decimal for 0.000000001", expected, actual, 0.0000000001);
	}

	@Test
	public void test_reverse_12345() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use the Integer library or the word Integer in your code.",usesLibrary("Math"));
		assertFalse("Don't use the StringBuilder library or the word StringBuilder in your code.",usesLibrary("StringBuilder"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		assertFalse("Don't use while statements.", usesConstruct("while"));
		assertFalse("Don't use for statements.", usesConstruct("for"));
		
		
		int expected = 54321;
		int actual = Expressions3.reverse(12345);
		
		assertEquals("testing 12345", expected, actual);
	}
	
	@Test
	public void test_reverse_00000() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use the Integer library or the word Integer in your code.",usesLibrary("Math"));
		assertFalse("Don't use the StringBuilder library or the word StringBuilder in your code.",usesLibrary("StringBuilder"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		assertFalse("Don't use while statements.", usesConstruct("while"));
		assertFalse("Don't use for statements.", usesConstruct("for"));
		
		
		int expected = 0;
		int actual = Expressions3.reverse(0);
		
		assertEquals("testing 00000", expected, actual);
	}

	@Test
	public void test_reverse_876543() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use the Integer library or the word Integer in your code.",usesLibrary("Math"));
		assertFalse("Don't use the StringBuilder library or the word StringBuilder in your code.",usesLibrary("StringBuilder"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		assertFalse("Don't use while statements.", usesConstruct("while"));
		assertFalse("Don't use for statements.", usesConstruct("for"));
		
		
		int expected = 34567;
		int actual = Expressions3.reverse(876543);
		
		assertEquals("testing 876543", expected, actual);
	}
	
	@Test
	public void test_reverse_veryLongNumber() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use the Integer library or the word Integer in your code.",usesLibrary("Math"));
		assertFalse("Don't use the StringBuilder library or the word StringBuilder in your code.",usesLibrary("StringBuilder"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		assertFalse("Don't use while statements.", usesConstruct("while"));
		assertFalse("Don't use for statements.", usesConstruct("for"));
		
		
		int expected = 54321;
		int actual = Expressions3.reverse(1234512345);
		
		assertEquals("testing 1234512345", expected, actual);
	}

	@Test
	public void test_reverse_ShortNumber() {
		assertFalse("Don't use the Math library or the word Math in your code.",usesLibrary("Math"));
		assertFalse("Don't use the Integer library or the word Integer in your code.",usesLibrary("Math"));
		assertFalse("Don't use the StringBuilder library or the word StringBuilder in your code.",usesLibrary("StringBuilder"));
		assertFalse("Don't use if statements.", usesConstruct("if"));
		assertFalse("Don't use while statements.", usesConstruct("while"));
		assertFalse("Don't use for statements.", usesConstruct("for"));
		
		
		int expected = 47600;
		int actual = Expressions3.reverse(674);
		
		assertEquals("testing 674", expected, actual);
	}


}
