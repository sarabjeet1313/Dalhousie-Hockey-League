package dpl.TeamManagementTest;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRetiredPlayerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

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
