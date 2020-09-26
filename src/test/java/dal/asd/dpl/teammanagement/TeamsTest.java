package dal.asd.dpl.teammanagement;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;


public class TeamsTest {
	
	@Test
	public void getTeamNameTest() {
		ArrayList<Players> playerList = null;
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		Assert.assertEquals("Boston", team.getTeamName());
	}
	
	@Test
	public void setTeamNameTest() {
		ArrayList<Players> playerList = null;
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		team.setTeamName("Dal Tigers");
		Assert.assertEquals("Dal Tigers", team.getTeamName());
	}
	
	@Test
	public void getGeneralManagerTest() {
		ArrayList<Players> playerList = null;
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		Assert.assertEquals("Mister Fred", team.getGeneralManager());
	}
	
	@Test
	public void setGeneralManagerTest() {
		ArrayList<Players> playerList = null;
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		team.setGeneralManager("John");
		Assert.assertEquals("John", team.getGeneralManager());
	}
	
	@Test
	public void getHeadCoachTest() {
		ArrayList<Players> playerList = null;
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		Assert.assertEquals("Mary Smith", team.getHeadCoach());
	}
	
	@Test
	public void setHeadCoachTest() {
		ArrayList<Players> playerList = null;
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		team.setHeadCoach("William");
		Assert.assertEquals("William", team.getHeadCoach());
	}
	
	@Test
	public void getPlayersTest() {
		ArrayList<Players> playerList = new ArrayList<Players>();
		Players player1 = new Players("Player1", "Forword", true);
		Players player2 = new Players("Player2", "Forword", true);
		Players player3 = new Players("Player3", "Goalie", true);
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		Assert.assertEquals(3, team.getPlayerList().size());
	}
	
	@Test
	public void setPlayersTest() {
		ArrayList<Players> playerList = new ArrayList<Players>();
		Players player1 = new Players("Player1", "Forword", false);
		Players player2 = new Players("Player2", "Forword", false);
		Players player3 = new Players("Player3", "Goalie", true);
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		Players player4 = new Players("Player4", "Defender", false);
		playerList.add(player4);
		team.setPlayerList(playerList);
		Assert.assertEquals(4, team.getPlayerList().size());
	}
	
	
}
