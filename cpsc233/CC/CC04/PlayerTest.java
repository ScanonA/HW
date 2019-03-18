import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;

public class PlayerTest {
	public class PlayerMock extends Player{
		int nextMove;
		
		public PlayerMock(int id){
			super(id);
		}
		
		public PlayerMock(PlayerMock c) {
			super(c);
		}
		
		public int chooseMove() {
			return nextMove;
		}	
	}

	public static final String CLASSNAME = "Player";
	public static final String FILENAME = CLASSNAME + ".java";
	
	private boolean hasRequiredAbstractMethods(String[] abstractMethods) {
		boolean[] methodsAbstract = new boolean[abstractMethods.length];
		for (int index = 0; index < abstractMethods.length; index++){
			methodsAbstract[index] = false;
		}
		boolean classIsAbstract = false;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String line = in.readLine();
			while (line != null) {
				if (line.contains("public abstract class " + CLASSNAME)){
					classIsAbstract = true;
				} else {
					for (int index = 0; index < abstractMethods.length; index++) {
						String stmt = "public abstract " + abstractMethods[index];
						if (line.contains(stmt)) {
							methodsAbstract[index] = true;
						}
					}
				}					
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			classIsAbstract = false;
		} catch (IOException e) {
			classIsAbstract = false;
		}
		
		boolean allAbstract = classIsAbstract;
		for (boolean b : methodsAbstract) {
			allAbstract = allAbstract && b;
		}
		return allAbstract;
	
	}
	
	private boolean hasRequiredProtectedMethods(String[] protectedMethods) {
		boolean[] methodsProtected = new boolean[protectedMethods.length];
		for (int index = 0; index < protectedMethods.length; index++){
			methodsProtected[index] = false;
		}
		boolean allProtected = true;

		try {
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String line = in.readLine();
			while (line != null) {
				for (int index = 0; index < protectedMethods.length; index++) {
					String stmt = "protected " + protectedMethods[index];
						if (line.contains(stmt)) {
							methodsProtected[index] = true;
						}
				}

				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			allProtected = false;
		} catch (IOException e) {
			allProtected = false;
		}

		for (boolean b : methodsProtected) {
			allProtected = allProtected && b;
		}
		return allProtected;
	}

	private boolean instanceVariablesArePrivate(String[] instanceVars){
		boolean[] varsPrivate = new boolean[instanceVars.length];
		for (int index = 0; index < instanceVars.length; index++){
			varsPrivate[index] = false;
		}
		boolean allPrivate = true;

		
		try {
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String line = in.readLine();
			while (line != null) {
				if (line.contains("private")) {
					line = line.trim();
					for (int index = 0; index < instanceVars.length; index++){
						String stmt = "private " + instanceVars[index];
						if (line.length() >= stmt.length()){
							String subline = line.substring(0,stmt.length());
							if (subline.equals(stmt)){
								varsPrivate[index] = true;
							}
						}
					}
				}
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			allPrivate = false;
		} catch (IOException e) {
			allPrivate =  false;
		}

		for (boolean b : varsPrivate) {
			allPrivate = allPrivate && b;
		}
		return allPrivate;
	}	
	
	private boolean noDefaultConstructor(){
		boolean noDefault = true;
		String[] versions = new String[9];
		versions[0] = "public " + CLASSNAME + "()";
		versions[1] = "public " + CLASSNAME + " ()";
		versions[2] = "public " + CLASSNAME + " ( )";
		versions[3] = "protected " + CLASSNAME + "()";
		versions[4] = "protected " + CLASSNAME + " ()";
		versions[5] = "protected " + CLASSNAME + " ( )";
		versions[6] = CLASSNAME + "()";
		versions[7] = CLASSNAME + " ()";
		versions[8] = CLASSNAME + " ( )";

		try {
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String line = in.readLine();
			while (line != null) {
				for (String stmt : versions) {
					if (line.contains(stmt)) {
						noDefault = false;
					}
				}
				line = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			noDefault = false;
		} catch (IOException e) {
			noDefault =  false;
		}
		return noDefault;
	}
	
	private void testClassDefinition(){
		String[] instanceVars = {"int id", "int score"};
		assertTrue("Instance variables should be private with correct name and type.", instanceVariablesArePrivate(instanceVars));

		assertTrue("Class should not have the default constructor.", noDefaultConstructor());
		
		String[] abstractMethods = {"int chooseMove()"};
		assertTrue("Class should have abstract method chooseMove that returns an int (minimize whitespace in signature).", hasRequiredAbstractMethods(abstractMethods));
		
		String[] protectedMethods = {"void setId", "int getId", "void updateScore"};
		assertTrue("Class should have protected methods setId, getId and updateScore.", hasRequiredProtectedMethods(protectedMethods));

	}
	
	
	// Testing constructors
	
		@Test
		public void testConstructor_id0(){
			testClassDefinition();
			Player c = new PlayerMock(0);
			assertEquals("Constructed Player(0) - testing id", 1111, c.getId());
			assertEquals("Constructed Player(0) - testing score", 0, c.getScore());			
		}

		@Test
		public void testConstructor_id1000(){
			testClassDefinition();
			Player c = new PlayerMock(1000);
			assertEquals("Constructed Player(1000) - testing id", 1000, c.getId());
			assertEquals("Constructed Player(1000) - testing score", 0, c.getScore());						
		}

		@Test
		public void testConstructor_id10000(){
			testClassDefinition();
			Player c = new PlayerMock(10000);
			assertEquals("Constructed Player(10000) - testing id", 1111, c.getId());
		}

		@Test
		public void testConstructor_id9999(){
			testClassDefinition();
			Player c = new PlayerMock(9999);
			assertEquals("Constructed Player(9999) - testing id", 9999, c.getId());
		}
		
		@Test
		public void testCopyConstructor(){
			testClassDefinition();
			PlayerMock p = new PlayerMock(1234);
			p.updateScore(56);
			Player p2 = new PlayerMock(p);
			assertEquals("Copied player with id 1234 and score 56 - testing id", 1234, p2.getId());
			assertEquals("Copied player with id 1234 and score 56 - testing score", 56, p2.getScore());
		}

		@Test
		public void testCopyConstructor2(){
			testClassDefinition();
			PlayerMock p = new PlayerMock(5485);
			p.updateScore(200);
			Player p2 = new PlayerMock(p);
			assertEquals("Copied player with id 5485 and score 200 - testing id", 5485, p2.getId());
			assertEquals("Copied player with id 5485 and score 200 - testing score", 200, p2.getScore());
		}

	// Testing setter and getters
	
		@Test
		public void test_setter_and_getter_id(){
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.setId(0);
			assertEquals("0, is not valid, expected default of 1111", 1111, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_1000(){
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.setId(1000);
			assertEquals("1000 is lowest valid ID.", 1000, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_9999(){
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.setId(9999);
			assertEquals("9999 is highest valid ID.", 9999, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_10000(){
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.setId(10000);
			assertEquals("10000 is not valid, expecting default 1111", 1111, c.getId());
		}
		
		@Test
		public void test_update_and_getter_score_zero() {
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.updateScore(20);
			c.updateScore(0);
			assertEquals("Updating by zero does not change score from 20.", 20, c.getScore());
		}

		@Test
		public void test_update_and_getter_score_negative() {
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.updateScore(30);
			c.updateScore(-1);
			assertEquals("Trying to update score by negative number should leave score unchanges from 30.", 30, c.getScore());
		}
		
		@Test
		public void test_update_and_getter_score_multipleUpdates() {
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.updateScore(20);
			c.updateScore(30);
			c.updateScore(40);
			assertEquals("Updating score three time: 20, 30, 40 should result in total score of 90.", 90, c.getScore());
		}
		
		@Test
		public void test_getLevel_beginner() {
			testClassDefinition();
			Player c = new PlayerMock(1000);
			assertEquals("Level for zero score is beginner", "beginner", c.getCategory());
			c.updateScore(5);
			assertEquals("Level for score 5 is beginner", "beginner", c.getCategory());
			c.updateScore(5);
			assertEquals("Level for score 10 is beginner", "beginner", c.getCategory());
		}

		@Test
		public void test_getLevel_intermediate() {
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.updateScore(11);
			assertEquals("Level for score 11 is intermediate", "intermediate", c.getCategory());
			c.updateScore(35);
			assertEquals("Level for score 46 is intermediate", "intermediate", c.getCategory());
			c.updateScore(54);
			assertEquals("Level for score 100 is intermediate", "intermediate", c.getCategory());
		}

		@Test
		public void test_getLevel_expert() {
			testClassDefinition();
			Player c = new PlayerMock(1000);
			c.updateScore(101);
			assertEquals("Level for score 101 is expert", "expert", c.getCategory());
			c.updateScore(1000);
			assertEquals("Level for score 1101 is expert", "expert", c.getCategory());
			c.updateScore(9998);
			assertEquals("Level for score 11099 is expert", "expert", c.getCategory());
		}
		
		//takeTurn testClassDefinition
		@Test
		public void test_takeTurn_zeroRemainder() {
			testClassDefinition();
			PlayerMock c = new PlayerMock(1000);
			c.updateScore(49);
			c.nextMove = 5;
			c.takeTurn();
			assertEquals("Old score: 49 (divisible by 7 so divisor should be 1 and next move is 5 points.", 54, c.getScore());
		}

		@Test
		public void test_takeTurn_RemainderOne() {
			testClassDefinition();
			PlayerMock c = new PlayerMock(1000);
			c.updateScore(15);
			c.nextMove = 49;
			c.takeTurn();
			assertEquals("Old score: 15 (remainder 1 after division by 7 so divisor should be 1 and next move is 49 points.", 64, c.getScore());
		}
		@Test
		public void test_takeTurn_Remainder3() {
			testClassDefinition();
			PlayerMock c = new PlayerMock(1000);
			c.updateScore(38);
			c.nextMove = 14;
			c.takeTurn();
			assertEquals("Old score: 38 (remainder 3 after division by 7 so divisor should be 3 and next move is 14 points.", 42, c.getScore());
		}
		@Test
		public void test_takeTurn_Remainder6() {
			testClassDefinition();
			PlayerMock c = new PlayerMock(1000);
			c.updateScore(13);
			c.nextMove = 12;
			c.takeTurn();
			assertEquals("Old score: 13 (remainder 6 after division by 7 so divisor should be 6 and next move is 12 points.", 15, c.getScore());
		}
		
		//ToString
		
		@Test
		public void test_toString() {
			testClassDefinition();
			Player c = new PlayerMock(2314);
			c.updateScore(21);

			assertEquals("ID: 2314, score 21", "ID: 2314 Score: 21", c.toString());
		}
		
		
}
