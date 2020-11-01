package dal.asd.dpl.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dal.asd.dpl.TeamManagement.IRetirementManagement;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.RetirementManagement;

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
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		Assert.assertTrue(leagueMockData.increaseAge(365, leagueData.getLeagueData()) instanceof League);
	}
}
