package dpl.LeagueManagementTest.TeamManagementTest;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Player;

public class RetirementManagementTest {
	LeagueObjectTestData leagueData = new LeagueObjectTestData();
	LeagueMockData leagueMockData = new LeagueMockData();
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	IRetirementManagement retirementManager = teamManagement.RetirementManagement();

	@Test
	public void getLikelihoodOfRetirementTest() {
		Player player = teamManagement.PlayerWithParameters("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 23, 3, 1999, Boolean.FALSE);
		int likelihood = retirementManager.getLikelihoodOfRetirement(leagueData.getLeagueData(), player);
		Assert.assertNotEquals(likelihood, 0);
	}

	@Test
	public void shouldPlayerRetireTest() {
		Player player = teamManagement.PlayerWithParameters("Player1", "Forward", false, 51, 1, 1, 1, 1, false, true, 0, false, 23, 3, 1999, Boolean.FALSE);
		Assert.assertTrue(leagueMockData.shouldPlayerRetire(leagueData.getLeagueData(), player));
	}

	@Test
	public void replaceRetiredPlayersTest() throws SQLException {
		Assert.assertTrue(leagueMockData.replaceRetiredPlayers(leagueData.getLeagueData()) instanceof League);
	}
	
	@Test
	public void increaseAgeTest() {
		League leagueData = new LeagueMockData().getTestData();
		Assert.assertTrue(leagueMockData.increaseAge(365, leagueData) instanceof League);
	}
}
