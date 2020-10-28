package dal.asd.dpl.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dal.asd.dpl.TeamManagement.IPlayerInjuryManagement;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.PlayerInjuryManagement;

public class PlayerInjuryManagementTest {
	
	IPlayerInjuryManagement playerManagement = new PlayerInjuryManagement();
	
	@Test
	public void updatePlayerInjuryStatusTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		playerManagement.updatePlayerInjuryStatus(leagueData.getLeagueData());
		Assert.assertTrue(playerManagement.updatePlayerInjuryStatus(leagueData.getLeagueData()) instanceof League);
	}

}
