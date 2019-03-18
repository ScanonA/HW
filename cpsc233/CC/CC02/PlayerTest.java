import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	// Testing setter and getters
	
		@Test
		public void test_setter_and_getter_id_0(){
			Player c = new Player();
			c.setId(0);
			assertEquals("0, is not valid, expected default of 1111", 1111, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_1000(){
			Player c = new Player();
			c.setId(1000);
			assertEquals("1000 is lowest valid ID.", 1000, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_9999(){
			Player c = new Player();
			c.setId(9999);
			assertEquals("9999 is highest valid ID.", 9999, c.getId());
		}
		
		@Test
		public void test_setter_and_getter_id_10000(){
			Player c = new Player();
			c.setId(10000);
			assertEquals("10000 is not valid, expecting default 1111", 1111, c.getId());
		}
		
		@Test
		public void test_update_and_getter_score_zero() {
			Player c = new Player();
			c.updateScore(20);
			c.updateScore(0);
			assertEquals("Updating by zero does not change score from 20.", 20, c.getScore());
		}

		@Test
		public void test_update_and_getter_score_negative() {
			Player c = new Player();
			c.updateScore(30);
			c.updateScore(-1);
			assertEquals("Trying to update score by negative number should leave score unchanges from 30.", 30, c.getScore());
		}
		
		@Test
		public void test_update_and_getter_score_multipleUpdates() {
			Player c = new Player();
			c.updateScore(20);
			c.updateScore(30);
			c.updateScore(40);
			assertEquals("Updating score three time: 20, 30, 40 should result in total score of 90.", 90, c.getScore());
		}
		
		@Test
		public void test_getLevel_beginner() {
			Player c = new Player();
			assertEquals("Level for zero score is beginner", "beginner", c.getLevel());
			c.updateScore(5);
			assertEquals("Level for score 5 is beginner", "beginner", c.getLevel());
			c.updateScore(5);
			assertEquals("Level for score 10 is beginner", "beginner", c.getLevel());
		}

		@Test
		public void test_getLevel_intermediate() {
			Player c = new Player();
			c.updateScore(11);
			assertEquals("Level for score 11 is intermediate", "intermediate", c.getLevel());
			c.updateScore(35);
			assertEquals("Level for score 46 is intermediate", "intermediate", c.getLevel());
			c.updateScore(54);
			assertEquals("Level for score 100 is intermediate", "intermediate", c.getLevel());
		}

		@Test
		public void test_getLevel_expert() {
			Player c = new Player();
			c.updateScore(101);
			assertEquals("Level for score 101 is expert", "expert", c.getLevel());
			c.updateScore(1000);
			assertEquals("Level for score 1101 is expert", "expert", c.getLevel());
			c.updateScore(9998);
			assertEquals("Level for score 11099 is expert", "expert", c.getLevel());
		}

		@Test
		public void test_getRating_emptyList() {
			Player c = new Player();
			Player[] list = new Player[0];
			assertEquals("Empty list has no players with higher score: player is first.", 1, c.getRating(list));
		}
		
		@Test
		public void test_getRating_ListHasOnePlayerWithHigherScore() {
			Player c = new Player();
			c.updateScore(20);
			c.setId(2222);
			Player[] list = new Player[1];
			list[0] = new Player();
			list[0].setId(3000);
			list[0].updateScore(100);
			assertEquals("List has one player with higher score", 2, c.getRating(list));
		}

		@Test
		public void test_getRating_ListHasTwoPlayersAllWithLowerOrSameScore() {
			Player c = new Player();
			c.updateScore(2000);
			c.setId(2222);
			Player[] list = new Player[2];
			list[0] = new Player();
			list[0].setId(3000);
			list[0].updateScore(100);
			list[1] = new Player();
			list[1].setId(3001);
			list[1].updateScore(2000);
			assertEquals("List has one player with lower score and one with same score.", 1, c.getRating(list));
		}

		@Test
		public void test_getRating_ListHasManyPlayers() {
			Player c = new Player();
			c.updateScore(2000);
			c.setId(2222);
			Player[] list = new Player[5];
			list[0] = new Player();
			list[0].setId(3000);
			list[0].updateScore(100);
			list[1] = new Player();
			list[1].setId(3001);
			list[1].updateScore(20000);
			list[2] = new Player();
			list[2].setId(3002);
			list[2].updateScore(5);
			list[3] = new Player();
			list[3].setId(3003);
			list[3].updateScore(2000);
			list[4] = new Player();
			list[4].setId(3004);
			list[4].updateScore(2001);
			assertEquals("List has many players, two with a higher score.", 3, c.getRating(list));
		}

		@Test
		public void test_getRating_ListIncludesPlayerWithSameID() {
			Player c = new Player();
			c.updateScore(2000);
			c.setId(2222);
			Player[] list = new Player[7];
			list[0] = new Player();
			list[0].setId(3000);
			list[0].updateScore(100);
			list[1] = new Player();
			list[1].setId(3001);
			list[1].updateScore(20000);
			list[2] = new Player();
			list[2].setId(3002);
			list[2].updateScore(5);
			list[3] = new Player();
			list[3].setId(3003);
			list[3].updateScore(2000);
			list[4] = new Player();
			list[4].setId(2222);
			list[4].updateScore(2001);
			list[5] = new Player();
			list[5].setId(3003);
			list[5].updateScore(2002);
			list[6] = new Player();
			list[6].setId(3003);
			list[6].updateScore(2003);
			assertEquals("List has many players, one with same id, which should be ignored.", 4, c.getRating(list));
		}
}
