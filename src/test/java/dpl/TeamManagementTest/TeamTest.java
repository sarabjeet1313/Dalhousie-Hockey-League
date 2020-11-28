package dpl.TeamManagementTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public class TeamTest {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	List<Player> playerList = new ArrayList<Player>();
	Coach headCoach = teamManagement.CoachWithParameters("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	Manager manager1 = teamManagement.ManagerWithParameters("Karen Potam", "normal");
	Team team = teamManagement.TeamWithParameters("Boston", manager1, headCoach, playerList, Boolean.FALSE);
	Player player1 = teamManagement.PlayerWithParameters("Player1", "Forword", false, 1, 1, 1, 1, 1, false, false, 0, false);
	Player player2 = teamManagement.PlayerWithParameters("Player2", "Forword", false, 1, 1, 1, 1, 1, false, false, 0, false);
	Player player3 = teamManagement.PlayerWithParameters("Player3", "Goalie", false, 1, 1, 1, 1, 1, false, false, 0, false);
	Player player4 = teamManagement.PlayerWithParameters("Player4", "Defender", false, 1, 1, 1, 1, 1, false, false, 0, false);
	LeagueMockData league = new LeagueMockData();
	private static final double DELTA = 1e-15;
	List<Player> playersList = new ArrayList<Player>();

	@Test
	public void parameterizedConstructorTest() {
		playerList.add(player1);
		team.setPlayerList(playerList);
		Assert.assertEquals("Boston", team.getTeamName());
		Assert.assertEquals(manager1.getManagerName(), team.getGeneralManager().getManagerName());
		Assert.assertEquals("Mary Smith", team.getHeadCoach().getCoachName());
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
		Assert.assertEquals(manager1.getManagerName(), team.getGeneralManager().getManagerName());
	}

	@Test
	public void setGeneralManagerTest() {
		team.setGeneralManager(manager1);
		Assert.assertEquals(manager1, team.getGeneralManager());
	}

	@Test
	public void getHeadCoachTest() {
		Assert.assertEquals("Mary Smith", team.getHeadCoach().getCoachName());
	}

	@Test
	public void setHeadCoachTest() {
		Coach headCoach = teamManagement.CoachWithParameters("William", 0.2, 0.3, 0.1, 0.4);
		team.setHeadCoach(headCoach);
		Assert.assertEquals("William", team.getHeadCoach().getCoachName());
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
		Team team1 = teamManagement.TeamWithParameters("Boston", manager1, headCoach, playerList, Boolean.FALSE);
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

	@Test
	public void getAvailablePlayersListTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		League league = teamManagement.League();
		List<List<Player>> list = league.getAvailableLeaguePlayers(leagueData.getLeagueData());
		Assert.assertEquals(3, list.size());
	}

	@Test
	public void getTeamStrengthTest() {
		String teamName = "Boston";
		double teamStrength = team.getTeamStrength(teamName, league.getTestData());
		Assert.assertNotEquals(7, teamStrength, DELTA);
	}

}
