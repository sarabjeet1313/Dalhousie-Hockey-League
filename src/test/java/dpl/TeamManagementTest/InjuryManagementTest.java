package dpl.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dpl.TeamManagement.IInjuryManagement;
import dpl.TeamManagement.InjuryManagement;
import dpl.TeamManagement.League;
import dpl.TeamManagement.Player;

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
		Assert.assertTrue(playerManagement.updatePlayerInjuryStatus(365, leagueData.getLeagueData()) instanceof League);
	}
	
	@Test
	public void getInjuryStatusByTeamTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		String teamName="Boston";
		Assert.assertTrue(playerManagement.getInjuryStatusByTeam(teamName, leagueData.getLeagueData()) instanceof League);
	}

}
