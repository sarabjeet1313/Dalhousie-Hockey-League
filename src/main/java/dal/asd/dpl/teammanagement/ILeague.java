package dal.asd.dpl.teammanagement;

import java.util.List;

public interface ILeague {
	
	public List<Leagues> getLeagueData(String teamName);
	
	public int checkLeagueName(String leagueName);
	
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName, 
			String generalManager, String headCoach, String playerName, String position, boolean captain);
	
}
