package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;

public class ManagerMockData implements IManagerPersistance{
	
	League league = new LeagueMockData().getTestData();
	
	@Override
	public boolean persistManagerInfo(String managerName, String teamName, String leagueName) {
		boolean isValid = false;
		if(league.getLeagueName().equals(leagueName)) {
			isValid = true;
		}
		return isValid;
	}
}
