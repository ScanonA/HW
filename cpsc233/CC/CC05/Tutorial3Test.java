import static org.junit.Assert.*;

import java.io.*;
import java.util.Scanner;

import org.junit.Test;

public class Tutorial3Test {
	
	private void createFile(String filename, String text){
		try {
			PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			output.print(text);
			output.close();
		} catch (IOException ioe) {
			fail("Unable to set up test environment, tried to (re)create file " + filename);
		}
	}
	
	private void createFile(String filename, double[] nums){
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));
			out.writeInt(nums.length);
			for (int counter = 0; counter < nums.length; counter++) {
				out.writeDouble(nums[counter]);
			}
			out.close();		
		} catch (IOException ioe) {
			fail("Unable to set up test environment, tried to create file " + filename);
		}
	}

	private void createFile(String filename, long[] nums){
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));
			out.writeInt(nums.length);
			for (int counter = 0; counter < nums.length; counter++) {
				out.writeLong(nums[counter]);
			}
			out.close();		
		} catch (IOException ioe) {
			fail("Unable to set up test environment, tried to create file " + filename);
		}
	}

	private void createFile(String filename, boolean[] bools){
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));
			out.writeInt(bools.length);
			for (int counter = 0; counter < bools.length; counter++) {
				out.writeBoolean(bools[counter]);
			}
			out.close();		
		} catch (IOException ioe) {
			fail("Unable to set up test environment, tried to create file " + filename);
		}
	}

	//Test with valid files
	
	@Test
	public void test1_getLetters() {
		Tutorial3 fl = new Tutorial3();
		fl.getLetters("test1.txt", "t1.txt");
		
		//Expected output
		String expected = "hawttuoc";
		
		//Read from the output file
		try {
			//Initialize scanner
			Scanner scan = new Scanner(new FileInputStream("t1.txt"));

			if (scan.hasNext()){
				//Read the first line in the file
				String line = scan.nextLine();
				assertEquals("Strings don't match.", expected, line);
			} else {
				//If there is nothing in the file, then the test fails
				fail("The output file is empty.");
			}
			
		} catch (FileNotFoundException fnfe){
			//If cannot open output file, then test fails
			fail("Cannot open the output file.");
		}
	}
	
	@Test
	public void test2_getLetters() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		fl.getLetters("test2.txt", "t2.txt");
		
		//Expected output
		String expected = "frdmvsghdlvk";
		
		//Read from the output file
		try {
			//Initialize scanner
			Scanner scan = new Scanner(new FileInputStream("t2.txt"));

			if (scan.hasNext()){
				//Read the first line in the file
				String line = scan.nextLine();
				assertEquals("Strings don't match.", expected, line);
			} else {
				//If there is nothing in the file, then the test fails
				fail("The output file is empty.");
			}
			
		} catch (FileNotFoundException fnfe){
			//If cannot open output file, then test fails
			fail("Cannot open the output file.");
		}
	}
	
	@Test
	public void test3_getLetters() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		fl.getLetters("test3.txt", "t3.txt");
		
		//Expected output
		String expected = "hw";
		
		//Read from the output file
		try {
			//Initialize scanner
			Scanner scan = new Scanner(new FileInputStream("t3.txt"));

			if (scan.hasNext()){
				//Read the first line in the file
				String line = scan.nextLine();
				assertEquals("Strings don't match.", expected, line);
			} else {
				//If there is nothing in the file, then the test fails
				fail("The output file is empty.");
			}
			
		} catch (FileNotFoundException fnfe){
			//If cannot open output file, then test fails
			fail("Cannot open the output file.");
		}
	}

	//Test with input file name as null
	
	@Test
	public void test4_getLetters_nullInputFile() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		fl.getLetters(null, "t4.txt");
		
		//Expected output
		String expected = "NullPointerException";
		
		//Read from the output file
		try {
			//Initialize scanner
			Scanner scan = new Scanner(new FileInputStream("t4.txt"));

			if (scan.hasNext()){
				//Read the first line in the file
				String line = scan.nextLine();
				assertEquals("Strings don't match.", expected, line);
			} else {
				//If there is nothing in the file, then the test fails
				fail("The output file is empty.");
			}
			
		} catch (FileNotFoundException fnfe){
			//If cannot open output file, then test fails
			fail("Cannot open the output file.");
		}
	}
	
	//Test with the input file doesn't exist
	
	@Test
	public void test5_getLetters_InvalidInputFileName() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		fl.getLetters("", "t5.txt");
		
		//Expected output
		String expected = "FileNotFoundException";
		
		//Read from the output file
		try {
			//Initialize scanner
			Scanner scan = new Scanner(new FileInputStream("t5.txt"));

			if (scan.hasNext()){
				//Read the first line in the file
				String line = scan.nextLine();
				assertEquals("Strings don't match.", expected, line);
			} else {
				//If there is nothing in the file, then the test fails
				fail("The output file is empty.");
			}
			
		} catch (FileNotFoundException fnfe){
			//If cannot open output file, then test fails
			fail("Cannot open the output file.");
		}
	}
	
	@Test
	public void test_count_noPInFile4() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		int actual = fl.count('p', "test4.txt");
		
		// verify result
		assertEquals("There is no 'p' in the file", 0, actual);
	}
	
	@Test
	public void test_count_oneCInFile4() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		int actual = fl.count('c', "test4.txt");
		
		// verify result
		assertEquals("There is one 'c' in the file", 1, actual);
	}
	
	@Test
	public void test_count_elevenTInFile4() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		int actual = fl.count('t', "test4.txt");
		
		// verify result
		assertEquals("There are 11 t's in the file (including an upper case 't'", 11, actual);
	}
	
	@Test
	public void test_count_invalidFileName() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		int actual = fl.count('f', "invalid.txt");
		
		// verify result
		assertEquals("The file invalid.txt does not exist", -1, actual);
	}
	
	@Test
	public void test_append_validFile() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		fl.append(true, "test5.txt");
		
		//Read from the output file
		try {
			//Initialize scanner
			Scanner scan = new Scanner(new FileInputStream("test5.txt"));

			if (scan.hasNext()){
				//Read the first line in the file
				String line = scan.nextLine();
				assertEquals("Expected 'one' to remain on first line in file", "one", line);
			} else {
				//If there is nothing in the file, then the test fails
				fail("The output file is empty.");
			}
			
			if (scan.hasNext()){
				//Read the second line in the file
				String line = scan.nextLine();
				assertEquals("Expected 'true' to be added to the file", "true", line);
			} else {
				//If there is nothing in the file, then the test fails
				fail("The output file does not have additional line.");
			}
			if (scan.hasNext()){
				fail("The file to append to had more lines than expected");
			} 

		} catch (FileNotFoundException fnfe){
			//If cannot open output file, then test fails
			fail("Cannot open the output file.");
		}
		
		createFile("test5.txt", "one\n");
	}	
	@Test
	public void test_append_newFile() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		fl.append(false, "t6.txt");
		
		//Read from the output file
		try {
			//Initialize scanner
			Scanner scan = new Scanner(new FileInputStream("t6.txt"));

			if (scan.hasNext()){
				//Read the first line in the file
				String line = scan.nextLine();
				assertEquals("Expected 'false' to be first line in new file", "false", line);
			} else {
				//If there is nothing in the file, then the test fails
				fail("The output file is empty.");
			}
			
			if (scan.hasNext()){
				fail("The file to append to had more lines than expected");
			} 
			scan.close();
			
		} catch (FileNotFoundException fnfe){
			//If cannot open output file, then test fails
			fail("Cannot open the output file.");
		}
		
		File f = new File("t6.txt");
		if (!f.delete()){
			fail("Unable to delete file created for test.  Make sure to manually delete t6.txt to prevent further failed tests.");
		}
	}	
	
	@Test
	public void test_addLongs_invalidFileName() {
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		long actual = fl.addLongs("invalid.bin");
		
		// verify result
		assertEquals("The file invalid.bin does not exist", -1, actual);
	}
	
	@Test
	public void test_addLongs_oneLongInFile() {
		long[] nums = {51};
		createFile("one.bin", nums);
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		long actual = fl.addLongs("one.bin");
		
		// verify result
		assertEquals("The file contains one long: 51", 51, actual);
	}
	
	@Test
	public void test_sumLongs_fiveNumsInFile() {
		long[] nums = {5, -1, 756, 45, -10};
		createFile("one.bin", nums);
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		long actual = fl.addLongs("one.bin");
		
		// verify result
		assertEquals("The file contains five numbers: 5, -1, 756, 45, -10", 795, actual);
	}
	@Test
	public void test_addLongs_manyNumsInFile() {
		long[] nums = {5, -1, 756, 45, -10, -456, 0, 21, -234, 4, 0, 41, 21, -1};
		createFile("one.bin", nums);
		
		//Initialize Tutorial3
		Tutorial3 fl = new Tutorial3();
		
		// run the test
		long actual = fl.addLongs("one.bin");
		
		// verify result
		assertEquals("The file contains many numbers", 191, actual);
	}


}
