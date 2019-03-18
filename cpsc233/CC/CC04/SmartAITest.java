import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;

public class SmartAITest {
	public static final String CLASSNAME = "SmartAI";
	public static final String FILENAME = CLASSNAME + ".java";
	
	
	private boolean hasMethod(String signature) {
		boolean contains = false;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String line = in.readLine();
			while (line != null) {
				if (line.contains(signature)) {
					contains = true;
				}
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			contains = false;
		} catch (IOException e) {
			contains =  false;
		}
		return contains;
	
	}	
	
	private boolean toStrInvokesParentToStr(){
		boolean callsGetter = false;
		boolean callsParent = false;
		
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String line = in.readLine();
			while (line != null) {
				if (line.contains("toString")) {
					// This should be start of toString method
					while (!line.contains("}")) {
						line = in.readLine();
						if (line.contains("getScore") || line.contains("getId")){
							callsGetter = true;
						}
						if (line.contains("super.toString()")) {
							callsParent = true;
						}
					}
				}
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			callsParent = false;
		} catch (IOException e) {
			callsParent =  false;
		}
		return callsParent && !callsGetter;
	}
	
	private void testInterface() {
		assertTrue("Should override toString and it should invoke parent toString (not getter methods in parent).", toStrInvokesParentToStr());
		assertFalse("Should not override takeTurn.", hasMethod("void takeTurn()"));
		assertFalse("Should not override (or call) updateScore.", hasMethod("updateScore"));
	}
	
	
	// Testing constructors
	
		@Test
		public void test_Constructor_GoodId(){
			testInterface();
			SmartAI c = new SmartAI(1234);
			assertEquals("Created smartAI with valid 1234 id.", 1234, c.getId());
		}
		
		
		@Test
		public void test_CopyConstructor() {
			testInterface();
			SmartAI c = new SmartAI(4321);
			SmartAI c2 = new SmartAI(c);
			assertEquals("Created smartAI with 4321 id.", 4321, c2.getId());
		}
		
		@Test
		public void test_CopyConstructor2() {
			testInterface();
			SmartAI c = new SmartAI(5467);
			SmartAI c2 = new SmartAI(c);
			assertEquals("Created smartAI with 5467 id.", 5467, c2.getId());
		}


		
		@Test
		public void test_chooseMove_from0Score() {
			testInterface();
			SmartAI c = new SmartAI(1234);
			int score = c.chooseMove();
			assertEquals("Current score is 0", 99, score);
		}

		@Test
		public void test_chooseMove_from6Score() {
			testInterface();
			SmartAI c = new SmartAI(1234);
			c.updateScore(6);
			int score = c.chooseMove();
			assertEquals("Current score is 6", 100, score);
		}

		@Test
		public void test_chooseMove_from11Score() {
			testInterface();
			SmartAI c = new SmartAI(1234);
			c.updateScore(11);
			int score = c.chooseMove();
			assertEquals("Current score is 11", 95, score);
		}

		@Test
		public void test_chooseMove_from109Score() {
			testInterface();
			SmartAI c = new SmartAI(1234);
			c.updateScore(109);
			int score = c.chooseMove();
			assertEquals("Current score is 109", 95, score);
		}

		// ToString
		
		@Test
		public void test_toString() {
			testInterface();
			SmartAI c = new SmartAI(9876);
			c.updateScore(123);

			assertEquals("[Smart] ID: 9876, score 123", "[Smart] ID: 9876 Score: 123", c.toString());
		}
		
		
}
