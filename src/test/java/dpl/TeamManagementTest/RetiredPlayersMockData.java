package dpl.TeamManagementTest;

import dpl.TeamManagement.IRetiredPlayerPersistance;
import dpl.TeamManagement.League;
import dpl.TeamManagement.Player;

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
