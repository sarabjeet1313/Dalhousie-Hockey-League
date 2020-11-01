package dal.asd.dpl.TeamManagementTest;

import dal.asd.dpl.TeamManagement.IRetiredPlayerPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;

public class RetiredPlayersMockData implements IRetiredPlayerPersistance {
	
	League league = new LeagueMockData().getTestData();

	@Override
	public boolean persisitRetiredPlayers(Player player, String teamName, League league) {
		boolean isValid = false;
		if(league.getLeagueName().equals(league.getLeagueName())) {
			isValid = true;
		}
		return isValid;
	}
}
