import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;

public class RandomAITest {
	public static final String CLASSNAME = "RandomAI";
	public static final String FILENAME = CLASSNAME + ".java";
	
	
	// checks if rating is the only private thing in the class.
	private boolean instanceVariablesArePrivate(){
		boolean ratingIsOnlyPrivate = true;
		boolean ratingIsPrivate = false;
		
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String line = in.readLine();
			while (line != null) {
				if (line.contains("private")) {
					line = line.trim();
					if (line.length() >=17) {
						line = line.substring(0,17);
						if (line.equals("private int level")){
							ratingIsPrivate = true;
						} else {
							ratingIsOnlyPrivate = false;
						}
					} else {
						ratingIsOnlyPrivate = false;
					}
				}
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			ratingIsPrivate = false;
		} catch (IOException e) {
			ratingIsPrivate =  false;
		}
		return ratingIsPrivate && ratingIsOnlyPrivate;
	}
	
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
		assertTrue("Should only have one instance variable (level).",instanceVariablesArePrivate());
		assertTrue("Should override toString and it should invoke parent toString (not getter methods in parent).", toStrInvokesParentToStr());
		assertFalse("Should not override takeTurn.", hasMethod("void takeTurn()"));
		assertFalse("Should not override (or call) updateScore.", hasMethod("updateScore"));
	}
	
	
	// Testing constructors
	
		@Test
		public void test_Constructor_level_Zero_GoodId(){
			testInterface();
			RandomAI c = new RandomAI(1234,0);
			assertEquals("Created randomAI with invalid 0 level", 1, c.getLevel());
			assertEquals("Created randomAI with valid 1234 id.", 1234, c.getId());
		}
		
		@Test
		public void test_Constructor_level_11_GoodId(){
			testInterface();
			RandomAI c = new RandomAI(1234,11);
			assertEquals("Created randomAI with invalid 11 level", 1, c.getLevel());
			assertEquals("Created randomAI with valid 1234 id.", 1234, c.getId());
		}
		
		@Test
		public void test_Constructor_level_3(){
			testInterface();
			RandomAI c = new RandomAI(1234,3);
			assertEquals("Created randomAI with valid evel 3", 3, c.getLevel());
		}
		
		@Test
		public void test_CopyConstructor() {
			testInterface();
			RandomAI c = new RandomAI(4321,10);
			RandomAI c2 = new RandomAI(c);
			assertEquals("Copied randomAI with level 10", 10, c2.getLevel());
			assertEquals("Created randomAI with 4321 id.", 4321, c2.getId());
		}
		
		@Test
		public void test_CopyConstructor2() {
			testInterface();
			RandomAI c = new RandomAI(5467, 7);
			RandomAI c2 = new RandomAI(c);
			assertEquals("Copied randomAI with 7 level", 7, c2.getLevel());
			assertEquals("Created randomAI with 5467 id.", 5467, c2.getId());
		}


	// Testing setter and getters
	
		@Test
		public void test_setter_and_getter_level_0(){
			testInterface();
			RandomAI c = new RandomAI(5555,2);
			c.setLevel(0);
			assertEquals("Set level to invalid 0, should have left unchanged from 2", 2, c.getLevel());
		}
		
		@Test
		public void test_setter_and_getter_level_1(){
			testInterface();
			RandomAI c = new RandomAI(5555,2);
			c.setLevel(1);
			assertEquals("Set level to lowest valid: 1", 1, c.getLevel());
		}
		
		@Test
		public void test_setter_and_getter_level_99(){
			testInterface();
			RandomAI c = new RandomAI(5555,2);
			c.setLevel(10);
			assertEquals("Set level to highest valid: 10", 10, c.getLevel());
		}
		
		@Test
		public void test_setter_and_getter_level_100(){
			testInterface();
			RandomAI c = new RandomAI(5555,2);
			c.setLevel(11);
			assertEquals("Set level to invalid 11, should have been unchanged from 2", 2, c.getLevel());
		}
		
		@Test
		public void test_chooseMove_from0ScoreLevel1() {
			testInterface();
			int[] scores = new int[10];
			for (int index = 0; index < 10; index ++) {
				scores[index] = 0;
			}
			
			for (int counter = 0; counter < 10000; counter++){
				RandomAI c = new RandomAI(1234, 1);
				int score = c.chooseMove();
				assertFalse("At level one, move should be between 0 and 9 points (inclusive), not " + score, score<0 || score>9);
				scores[score]++;
			}
			
			assertTrue("Expected 0 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[0] > 0);
			assertTrue("Expected 1 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[1] > 0);
			assertTrue("Expected 2 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[2] > 0);
			assertTrue("Expected 3 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[3] > 0);
			assertTrue("Expected 4 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[4] > 0);
			assertTrue("Expected 5 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[5] > 0);
			assertTrue("Expected 6 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[6] > 0);
			assertTrue("Expected 7 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[7] > 0);
			assertTrue("Expected 8 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[8] > 0);
			assertTrue("Expected 9 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[9] > 0);
		}

		@Test
		public void test_chooseMove_from0ScoreLevel9() {
			testInterface();
			int[] scores = new int[10];
			for (int index = 0; index < 10; index ++) {
				scores[index] = 0;
			}
			
			for (int counter = 0; counter < 10000; counter++){
				RandomAI c = new RandomAI(1234, 9);
				int score = c.chooseMove();
				assertFalse("At level nine, move should be between 0 and 81 points (inclusive), not " + score, score<0 || score>81);
				scores[score/9]++;
			}
			
			assertTrue("Expected 0 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[0] > 0);
			assertTrue("Expected 9 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[1] > 0);
			assertTrue("Expected 18 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[2] > 0);
			assertTrue("Expected 27 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[3] > 0);
			assertTrue("Expected 36 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[4] > 0);
			assertTrue("Expected 45 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[5] > 0);
			assertTrue("Expected 54 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[6] > 0);
			assertTrue("Expected 63 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[7] > 0);
			assertTrue("Expected 72 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[8] > 0);
			assertTrue("Expected 81 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[9] > 0);
		}

		@Test
		public void test_chooseMove_from0ScoreLevel5() {
			testInterface();
			int[] scores = new int[10];
			for (int index = 0; index < 10; index ++) {
				scores[index] = 0;
			}
			
			for (int counter = 0; counter < 10000; counter++){
				RandomAI c = new RandomAI(1234, 5);
				int score = c.chooseMove();
				assertFalse("At level five, move should be between 0 and 45 points (inclusive), not " + score, score<0 || score>45);
				scores[score/5]++;
			}
			
			assertTrue("Expected 0 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[0] > 0);
			assertTrue("Expected 5 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[1] > 0);
			assertTrue("Expected 10 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[2] > 0);
			assertTrue("Expected 15 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[3] > 0);
			assertTrue("Expected 20 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[4] > 0);
			assertTrue("Expected 25 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[5] > 0);
			assertTrue("Expected 30 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[6] > 0);
			assertTrue("Expected 35 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[7] > 0);
			assertTrue("Expected 40 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[8] > 0);
			assertTrue("Expected 45 to be randomly chosen at least once when doing 10000 random choices, but it was not.", scores[9] > 0);
		}

		
		// ToString
		
		@Test
		public void test_toString() {
			testInterface();
			RandomAI c = new RandomAI(3421, 5);
			c.updateScore(50);

			assertEquals("[Random] ID: 3421, score 50 and level 5", "[Random] ID: 3421 Score: 50 Level: 5", c.toString());
		}
		
		
}
