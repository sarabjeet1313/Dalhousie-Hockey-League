package dal.asd.dpl.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dal.asd.dpl.TeamManagement.IInjuryManagement;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.InjuryManagement;

public class InjuryManagementTest {
	
	IInjuryManagement playerManagement = new InjuryManagement();
	Player player = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false, 0);
	
	@Test
	public void getPlayerInjuryDaysTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		Assert.assertTrue(playerManagement.getPlayerInjuryDays(player, leagueData.getLeagueData()) instanceof Player);
	}
	
	@Test
	public void updatePlayerInjuryStatusTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		Assert.assertTrue(playerManagement.updatePlayerInjuryStatus(leagueData.getLeagueData()) instanceof League);
	}
	
	@Test
	public void getInjuryStatusByTeamTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		String teamName="Boston";
		Assert.assertTrue(playerManagement.getInjuryStatusByTeam(teamName, leagueData.getLeagueData()) instanceof League);
	}

}
