package dal.asd.dpl.TeamManagementTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import dal.asd.dpl.TeamManagement.ITeamInfo;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.TeamInfo;

public class TeamInfoTest {

	ITeamInfo teamInfo = new TeamInfo();
	private static final double DELTA = 1e-15;
	List<Player> playersList = new ArrayList<Player>();
	
	@Test
	public void getTeamStrengthTest() {
		Player player1 = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1, false);
		Player player2 = new Player("Player2", "Defense", false, 1, 1, 1, 1, 1, true);
		Player player3 = new Player("Player3", "Goalie", false, 1, 1, 1, 1, 1, false);
		playersList.add(player1);		
		playersList.add(player2);
		playersList.add(player3);
		double teamStrength = teamInfo.getTeamStrength(playersList);
		Assert.assertEquals(5.75, teamStrength, DELTA);
	}
	
	@Test
	public void getTeamStrengthTwoTest() {
		Player player1 = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1, false);
		Player player2 = new Player("Player2", "Defense", false, 1, 1, 1, 1, 1, false);
		Player player3 = new Player("Player3", "Goalie", false, 1, 1, 1, 1, 1, false);
		playersList.add(player1);		
		playersList.add(player2);
		playersList.add(player3);
		double teamStrength = teamInfo.getTeamStrength(playersList);
		Assert.assertEquals(7, teamStrength, DELTA);
	}
	
	@Test
	public void getTeamStrengthThreeTest() {
		Player player1 = new Player("Player1", "Forward", false, 1, 1, 5, 1, 1, false);
		Player player2 = new Player("Player2", "Defense", false, 2, 1, 1, 1, 1, false);
		Player player3 = new Player("Player3", "Goalie", false, 1, 1, 3, 1, 1, false);
		playersList.add(player1);		
		playersList.add(player2);
		playersList.add(player3);
		double teamStrength = teamInfo.getTeamStrength(playersList);
		Assert.assertNotEquals(7.5, teamStrength, DELTA);
	}
}
