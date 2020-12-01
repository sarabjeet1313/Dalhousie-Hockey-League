package dpl.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.AllStarGameManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IAllStarGameManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public class AllStarGameManagementTest {
	
	League leagueData = new LeagueMockData().getTestData();
	IAllStarGameManagement star = new AllStarGameManagement();

	@Test
	public void performAllStarGameTest() {
		Assert.assertEquals(2, star.performAllStarGame(leagueData).size());
	}
	
	@Test
	public void getSortedPlayersByTypeTest() {
		Assert.assertTrue(star.getSortedPlayersByType(leagueData.getConferenceList(), "Forward").size() > 0);
	}
	
	@Test
	public void getPlayersBytTeamTest() {
		Assert.assertTrue(star.getPlayersBytTeam().get(0).size() > 0);
	}
	
}
