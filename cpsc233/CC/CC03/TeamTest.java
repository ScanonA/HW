import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class TeamTest {
	
		@Test
		public void test_ConstructorAndGetter() {
			Team c = new Team("Test Constructor and Getter");
			assertEquals("Testing constructor and getter", "Test Constructor and Getter", c.getName());
		}
		
		@Test
		public void test_addPlayer_addingOne() {
			Team c = new Team("Test");
			Player m = new Player(2222,2);
			c.addPlayer(m);
			ArrayList<Player> list = c.getPlayerList();
			Player m2 = null;
			
			if (list.size() > 0){
				m2 = list.get(0);
			}
			
			
			assertEquals("Team only has one player (2222,2) - testing size.", 1, list.size());
			assertEquals("Team only has one player (2222,2) - testing id.", 2222, m2.getId());
			assertEquals("Team only has one player (2222,2)- testing score.", 2, m2.getScore());
		}

		@Test
		public void test_addPlayer_addingMany() {
			Team c = new Team("Test");
			Player m1 = new Player(3000,100);
			Player m2 = new Player(3001,20000);
			Player m3 = new Player(3002,5);
			Player m4 = new Player(3003,2000);
			Player m5 = new Player(3004,2001);
			Player m6 = new Player(3005, 49);
			c.addPlayer(m1);
			c.addPlayer(m2);
			c.addPlayer(m3);
			c.addPlayer(m4);
			c.addPlayer(m5);
			c.addPlayer(m6);
			
			ArrayList<Player> list = c.getPlayerList();
			
			assertEquals("Expected list of size 6 after adding 6 players", 6, list.size());			
			assertEquals("Player 1 test - testing id", 3000, list.get(0).getId());
			assertEquals("Player 2 test - testing id", 3001, list.get(1).getId());
			assertEquals("Player 3 test - testing id", 3002, list.get(2).getId());
			assertEquals("Player 4 test - testing id", 3003, list.get(3).getId());
			assertEquals("Player 5 test - testing id", 3004, list.get(4).getId());
			assertEquals("Player 6 test - testing id", 3005, list.get(5).getId());
		}

		@Test
		public void test_addPlayer_addingOne_EncapsulationTest() {
			Team c = new Team("Test");
			Player m = new Player(4532,41);
			c.addPlayer(m);
			m.setId(2321);
			ArrayList<Player> list = c.getPlayerList();
			Player m2 = null;
			
			if (list.size() > 0){
				m2 = list.get(0);
			}
			
			assertEquals("Team only has one player (4532,41)- testing encapsulation (changed id of original).", 4532, m2.getId());
			
		}
		
		@Test
		public void test_getPlayerList_Encapsulation() {
			Team c = new Team("Test");
			Player m1 = new Player(3000,100);
			Player m2 = new Player(3001,20000);
			Player m3 = new Player(3002,5);
			Player m4 = new Player(3003,2000);
			Player m5 = new Player(3004,2001);
			Player m6 = new Player(3005, 49);
			c.addPlayer(m1);
			c.addPlayer(m2);
			c.addPlayer(m3);
			c.addPlayer(m4);
			c.addPlayer(m5);
			c.addPlayer(m6);
			
			ArrayList<Player> list = c.getPlayerList();
			
			list.get(0).setId(1111);
			list.get(1).setId(2222);
			list.get(2).setId(3333);
			list.get(3).setId(4444);
			list.get(4).setId(5555);
			list.get(5).setId(6666);
			
			list = c.getPlayerList();
			
			
			assertEquals("Player 1 test - testing id", 3000, list.get(0).getId());
			assertEquals("Player 2 test - testing id", 3001, list.get(1).getId());
			assertEquals("Player 3 test - testing id", 3002, list.get(2).getId());
			assertEquals("Player 4 test - testing id", 3003, list.get(3).getId());
			assertEquals("Player 5 test - testing id", 3004, list.get(4).getId());
			assertEquals("Player 6 test - testing id", 3005, list.get(5).getId());
			
		}

		@Test
		public void test_GetPlayerWithHighestScore_emptyList() {
			Team c = new Team("test");
			assertEquals("No players added to list.", null, c.getPlayerWithHighestScore());
		}
		
		@Test
		public void test_GetPlayerWithHighestScore_OnePlayerInTeam() {
			Team c = new Team("test");
			Player m = new Player(5555,55);
			c.addPlayer(m);
			Player highest = c.getPlayerWithHighestScore();
			assertEquals("Team only has one player (5555,55) - testing id.", 5555, highest.getId());
			assertEquals("Team only has one player (5555,55)- testing score.", 55, highest.getScore());
		}

		@Test
		public void test_GetPlayerWithHighestScore_ListHasTwoPlayersWithSameScore() {
			Team c = new Team("test");
			Player m1 = new Player(5555,100);
			Player m2 = new Player(3333,100);
			c.addPlayer(m1);
			c.addPlayer(m2);
			
			Player highest = c.getPlayerWithHighestScore();
			
			assertEquals("Team only has two players with same score, expected to get first (5555,100) - testing id.", 5555, highest.getId());
			assertEquals("Team only has two players with same score, expected to get first added (5555,100) - testing score.", 100, highest.getScore());
		}
		
		@Test
		public void test_GetPlayerWithHighestScore_highestInMiddle() {
			Team c = new Team("test");
			Player m1 = new Player(7500, 2);
			Player m2 = new Player(6600, 5);
			Player m3 = new Player(9900, 1);
			c.addPlayer(m1);
			c.addPlayer(m2);
			c.addPlayer(m3);
			
			Player highest = c.getPlayerWithHighestScore();
			
			assertEquals("Team has three players with most bounces in middle (6600,5) - testing id.", 6600, highest.getId());
			assertEquals("Team has three players with highest score in middle (6600,5)- testing score.", 5, highest.getScore());
		}
		
		@Test
		public void test_GetPlayerWithHighestScore_LastHasHighest() {
			Team c = new Team("test");
			Player m1 = new Player(9900, 1);
			Player m2 = new Player(5000, 2);
			Player m3 = new Player(5500, 3);
			Player m4 = new Player(6500, 5);
			Player m5 = new Player(9800, 9);
			Player m6 = new Player(7600, 10);
			c.addPlayer(m1);
			c.addPlayer(m2);
			c.addPlayer(m3);
			c.addPlayer(m4);
			c.addPlayer(m5);
			c.addPlayer(m6);
			
			Player highest = c.getPlayerWithHighestScore();
			
			assertEquals("Team has many players with highest score at end (7600,10) - testing id.", 7600, highest.getId());
			assertEquals("Team has many players with highest score at end (7600,10)- testing score.", 10, highest.getScore());

			highest.setId(3300);
			Player b = c.getPlayerList().get(5);
			
			assertEquals("Team has three players with highest score at end (7600,10)- testing encapsulation.", 7600, b.getId());
		}
		
}
