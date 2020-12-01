package dpl.LeagueManagementTest.TeamManagementTest;

import org.junit.Assert;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Player;

public class InjuryManagementTest {
	
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	IInjuryManagement playerManagement = teamManagement.InjuryManagement();
	Player player = teamManagement.PlayerWithParameters("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	
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
