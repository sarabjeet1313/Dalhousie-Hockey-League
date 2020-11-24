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
	public void getTeamsForAllStarGameTest() {
		Assert.assertEquals(2, star.getTeamsForAllStarGame(leagueData).size());
	}
	
}
