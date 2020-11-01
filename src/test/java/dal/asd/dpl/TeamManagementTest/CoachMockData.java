package dal.asd.dpl.TeamManagementTest;

import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.ICoachPersistance;
import dal.asd.dpl.TeamManagement.League;

public class CoachMockData implements ICoachPersistance {
	
	League league = new LeagueMockData().getTestData(); 
	@Override
	public boolean persisitCoaches(Coach coach, String teamName, String leagueName) {
		boolean isValid = false;
		for(int index = 0; index < league.getCoaches().size(); index++) {
			if(coach.getCoachName().equals(league.getCoaches().get(index).getCoachName())) {
				isValid = true;
			}
		}
		return isValid;
	}
}
