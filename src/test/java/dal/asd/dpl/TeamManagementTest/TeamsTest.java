package dal.asd.dpl.TeamManagementTest;

import java.util.ArrayList;
import org.junit.Test;
import dal.asd.dpl.TeamManagement.LeagueObjectTestData;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Teams;
import org.junit.Assert;


public class TeamsTest {
	
	ArrayList<Player> playerList = new ArrayList<Player>();
	Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
	Player player1 = new Player("Player1", "Forword", false, 1, 1, 1, 1, 1);
	Player player2 = new Player("Player2", "Forword", false, 1, 1, 1, 1, 1);
	Player player3 = new Player("Player3", "Goalie", false, 1, 1, 1, 1, 1);
	Player player4 = new Player("Player4", "Defender", false, 1, 1, 1, 1, 1);
	
	@Test
	public void parameterizedConstructorTest() {
		playerList.add(player1);
		team.setPlayerList(playerList);
		Assert.assertEquals("Boston", team.getTeamName());
		Assert.assertEquals("Mister Fred", team.getGeneralManager());
		Assert.assertEquals("Mary Smith", team.getHeadCoach());
		Assert.assertEquals(1, team.getPlayerList().size());
	}
	
	@Test
	public void getTeamNameTest() {
		Assert.assertEquals("Boston", team.getTeamName());
	}
	
	@Test
	public void setTeamNameTest() {
		team.setTeamName("Dal Tigers");
		Assert.assertEquals("Dal Tigers", team.getTeamName());
	}
	
	@Test
	public void getGeneralManagerTest() {
		Assert.assertEquals("Mister Fred", team.getGeneralManager());
	}
	
	@Test
	public void setGeneralManagerTest() {
		team.setGeneralManager("John");
		Assert.assertEquals("John", team.getGeneralManager());
	}
	
	@Test
	public void getHeadCoachTest() {
		Assert.assertEquals("Mary Smith", team.getHeadCoach());
	}
	
	@Test
	public void setHeadCoachTest() {
		team.setHeadCoach("William");
		Assert.assertEquals("William", team.getHeadCoach());
	}
	
	@Test
	public void getPlayersTest() {
		playerList.add(player2);
		team.setPlayerList(playerList);
		Assert.assertEquals(1, team.getPlayerList().size());
	}
	
	@Test
	public void setPlayersTest() {
		playerList.add(player1);
		playerList.add(player2);
		Teams team1 = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		playerList.add(player3);
		playerList.add(player4);
		team1.setPlayerList(playerList);
		Assert.assertEquals(4, team1.getPlayerList().size());
	}
	
	@Test
	public void isValidTeamNameTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		String teamName = "dal";
		String conferenceName = "Eastern Conference";
		String divisionName = "Atlantic";
		Assert.assertFalse(team.isValidTeamName(conferenceName, divisionName, teamName, leagueData.getLeagueData()));
	}
	
}
