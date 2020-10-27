package dal.asd.dpl.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dal.asd.dpl.TeamManagement.IRetirementManager;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.RetirementManagement;

public class RetirementManagementTest {
	LeagueObjectTestData leagueData = new LeagueObjectTestData();
	IRetirementManager retirementManager = new RetirementManagement();

	@Test
	public void getLikelihoodOfRetirementTest() {
		Player player = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false, 0);
		int likelihood = retirementManager.getLikelihoodOfRetirement(leagueData.getLeagueData(), player);
		Assert.assertNotEquals(likelihood, 0);
	}
	
	@Test
	public void shouldPlayerRetireTest() {
		Player player = new Player("Player1", "Forward", false, 51, 1, 1, 1, 1, false, true, 0);
		Assert.assertTrue(retirementManager.shouldPlayerRetire(leagueData.getLeagueData(), player));
	}
	
	@Test
	public void replaceRetiredPlayersTest() {
		Assert.assertTrue(retirementManager.replaceRetiredPlayers(leagueData.getLeagueData()) instanceof League);
	}
	
	@Test
	public void updateRetiredPlayersTest() {
		Player player = new Player("Player1", "Forward", false, 51, 1, 1, 1, 1, false, true, 0);
		Player playerResult = retirementManager.updateRetiredPlayers(player, leagueData.getLeagueData().getFreeAgents());
		Assert.assertTrue(playerResult instanceof Player);
	}
}
