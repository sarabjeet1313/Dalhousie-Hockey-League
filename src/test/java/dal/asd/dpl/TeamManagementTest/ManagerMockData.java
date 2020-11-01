package dal.asd.dpl.TeamManagementTest;

import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.TeamManagement.League;

public class ManagerMockData implements IManagerPersistance{
	
	League league = new LeagueMockData().getTestData();
	
	@Override
	public boolean persisitManagerInfo(String managerName, String teamName, String leagueName) {
		boolean isValid = false;
		if(league.getLeagueName().equals(leagueName)) {
			isValid = true;
		}
		return isValid;
	}
}
