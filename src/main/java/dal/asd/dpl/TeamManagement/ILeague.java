package dal.asd.dpl.TeamManagement;

import java.util.List;

public interface ILeague {
	
	public List<Leagues> getLeagueData(String teamName);
	
	public int checkLeagueName(String leagueName);
	
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName, 
			String generalManager, String headCoach, Player player);
	
	public boolean persisitCoaches(Coach coach, String teamName, String leagueName);
	
}
