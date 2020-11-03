package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Coach;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;

public class CoachMockData implements ICoachPersistance {
	
	League league = new LeagueMockData().getTestData(); 
	@Override
	public boolean persistCoaches(Coach coach, String teamName, String leagueName) {
		boolean isValid = false;
		for(int index = 0; index < league.getCoaches().size(); index++) {
			if(coach.getCoachName().equals(league.getCoaches().get(index).getCoachName())) {
				isValid = true;
			}
		}
		return isValid;
	}
}
