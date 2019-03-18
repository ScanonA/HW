import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	// Testing constructors
	
		@Test
		public void testConstructor_id0_score0(){
			Player c = new Player(0,0);
			assertEquals("Constructed Player(0,0) - testing id", 1111, c.getId());
			assertEquals("Constructed Player(0,0) - testing score", 0, c.getScore());			
		}

		@Test
		public void testConstructor_id1000_scoreNegative(){
			Player c = new Player(1000,-1);
			assertEquals("Constructed Player(1000,-1) - testing id", 1000, c.getId());
			assertEquals("Constructed Player(1000,-1) - testing score", 0, c.getScore());						
		}

		@Test
		public void testConstructor_id10000_scorePositive(){
			Player c = new Player(10000,55);
			assertEquals("Constructed Player(10000,55) - testing id", 1111, c.getId());
			assertEquals("Constructed Player(10000,55) - testing score", 55, c.getScore());						
		}

		@Test
		public void testConstructor_id9999_scoreLarge(){
			Player c = new Player(9999,69346);
			assertEquals("Constructed Player(9999,69346) - testing id", 9999, c.getId());
			assertEquals("Constructed Player(9999,69346) - testing score", 69346, c.getScore());						
		}
		
		@Test
		public void testCopyConstructor(){
			Player p = new Player(1234,56);
			Player p2 = new Player(p);
			assertEquals("Copied player with id 1234 and score 56 - testing id", 1234, p2.getId());
			assertEquals("Copied player with id 1234 and score 56 - testing score", 56, p2.getScore());
		}

		@Test
		public void testCopyConstructor2(){
			Player p = new Player(5485,200);
			Player p2 = new Player(p);
			assertEquals("Copied player with id 5485 and score 200 - testing id", 5485, p2.getId());
			assertEquals("Copied player with id 5485 and score 200 - testing score", 200, p2.getScore());
		}

	// Testing setter and getters
	
		@Test
		public void test_setter_and_getter_id_0(){
			Player c = new Player(1000,10);
			c.setId(0);
			assertEquals("0, is not valid, expected default of 1111", 1111, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_1000(){
			Player c = new Player(1000,10);
			c.setId(1000);
			assertEquals("1000 is lowest valid ID.", 1000, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_9999(){
			Player c = new Player(1000,10);
			c.setId(9999);
			assertEquals("9999 is highest valid ID.", 9999, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_10000(){
			Player c = new Player(1000,10);
			c.setId(10000);
			assertEquals("10000 is not valid, expecting default 1111", 1111, c.getId());
		}
		
		@Test
		public void test_update_and_getter_score_zero() {
			Player c = new Player(1000,0);
			c.updateScore(20);
			c.updateScore(0);
			assertEquals("Updating by zero does not change score from 20.", 20, c.getScore());
		}

		@Test
		public void test_update_and_getter_score_negative() {
			Player c = new Player(1000,0);
			c.updateScore(30);
			c.updateScore(-1);
			assertEquals("Trying to update score by negative number should leave score unchanges from 30.", 30, c.getScore());
		}
		
		@Test
		public void test_update_and_getter_score_multipleUpdates() {
			Player c = new Player(1000,0);
			c.updateScore(20);
			c.updateScore(30);
			c.updateScore(40);
			assertEquals("Updating score three time: 20, 30, 40 should result in total score of 90.", 90, c.getScore());
		}
		
		@Test
		public void test_getLevel_beginner() {
			Player c = new Player(1000,0);
			assertEquals("Level for zero score is beginner", "beginner", c.getLevel());
			c.updateScore(5);
			assertEquals("Level for score 5 is beginner", "beginner", c.getLevel());
			c.updateScore(5);
			assertEquals("Level for score 10 is beginner", "beginner", c.getLevel());
		}

		@Test
		public void test_getLevel_intermediate() {
			Player c = new Player(1000,0);
			c.updateScore(11);
			assertEquals("Level for score 11 is intermediate", "intermediate", c.getLevel());
			c.updateScore(35);
			assertEquals("Level for score 46 is intermediate", "intermediate", c.getLevel());
			c.updateScore(54);
			assertEquals("Level for score 100 is intermediate", "intermediate", c.getLevel());
		}

		@Test
		public void test_getLevel_expert() {
			Player c = new Player(1000,0);
			c.updateScore(101);
			assertEquals("Level for score 101 is expert", "expert", c.getLevel());
			c.updateScore(1000);
			assertEquals("Level for score 1101 is expert", "expert", c.getLevel());
			c.updateScore(9998);
			assertEquals("Level for score 11099 is expert", "expert", c.getLevel());
		}
/**
		@Test
		public void test_getRating_emptyList() {
			Player c = new Player(1000,0);
			Player[] list = new Player[0];
			assertEquals("Empty list has no players with higher score: player is first.", 1, c.getRating(list));
		}
		
		@Test
		public void test_getRating_ListHasOnePlayerWithHigherScore() {
			Player c = new Player(2222,20);
			Player[] list = new Player[1];
			list[0] = new Player(3000,100);
			assertEquals("List has one player with higher score", 2, c.getRating(list));
		}

		@Test
		public void test_getRating_ListHasTwoPlayersAllWithLowerOrSameScore() {
			Player c = new Player(2222,2000);
			Player[] list = new Player[2];
			list[0] = new Player(3000,100);
			list[1] = new Player(30001,2000);
			assertEquals("List has one player with lower score and one with same score.", 1, c.getRating(list));
		}

		@Test
		public void test_getRating_ListHasManyPlayers() {
			Player c = new Player(2222,2000);
			Player[] list = new Player[5];
			list[0] = new Player(3000,100);
			list[1] = new Player(3001,20000);
			list[2] = new Player(3002,5);
			list[3] = new Player(3003,2000);
			list[4] = new Player(3004,2001);
			assertEquals("List has many players, two with a higher score.", 3, c.getRating(list));
		}

		@Test
		public void test_getRating_ListIncludesPlayerWithSameID() {
			Player c = new Player(2222,2000);
			Player[] list = new Player[7];
			list[0] = new Player(3000,100);
			list[1] = new Player(3001,20000);
			list[2] = new Player(3002,5);
			list[3] = new Player(3003,2000);
			list[4] = new Player(2222,2001);
			list[5] = new Player(3005,2002);
			list[6] = new Player(3006,2003);
			assertEquals("List has many players, one with same id, which should be ignored.", 4, c.getRating(list));
		}*/
}
