package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.IRetirementManagement;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Player;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.RetirementManagement;

public class RetirementManagementTest {
	LeagueObjectTestData leagueData = new LeagueObjectTestData();
	LeagueMockData leagueMockData = new LeagueMockData();
	IRetirementManagement retirementManager = new RetirementManagement();

	@Test
	public void getLikelihoodOfRetirementTest() {
		Player player = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false, 0);
		int likelihood = retirementManager.getLikelihoodOfRetirement(leagueData.getLeagueData(), player);
		Assert.assertNotEquals(likelihood, 0);
	}

	@Test
	public void shouldPlayerRetireTest() {
		Player player = new Player("Player1", "Forward", false, 51, 1, 1, 1, 1, false, true, 0);
		Assert.assertTrue(leagueMockData.shouldPlayerRetire(leagueData.getLeagueData(), player));
	}

	@Test
	public void replaceRetiredPlayersTest() {
		Assert.assertTrue(leagueMockData.replaceRetiredPlayers(leagueData.getLeagueData()) instanceof League);
	}
	
	@Test
	public void increaseAgeTest() {
		League leagueData = new LeagueMockData().getTestData();
		Assert.assertTrue(leagueMockData.increaseAge(365, leagueData) instanceof League);
	}
}
