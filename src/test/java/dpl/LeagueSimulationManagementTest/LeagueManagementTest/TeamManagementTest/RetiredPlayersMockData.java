package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.IRetiredPlayerPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Player;

public class RetiredPlayersMockData implements IRetiredPlayerPersistance {
	
	League league = new LeagueMockData().getTestData();

	@Override
	public boolean persistRetiredPlayers(Player player, String teamName, League league) {
		boolean isValid = false;
		if(league.getLeagueName().equals(league.getLeagueName())) {
			isValid = true;
		}
		return isValid;
	}
}
