package dal.asd.dpl.teammanagement;

import java.sql.SQLException;
import java.util.List;

public interface ILeague {
	
	public List<Leagues> getLeagueData(String teamName) throws SQLException;
	
	public int checkLeagueName(String leagueName) throws SQLException;
	
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName, 
			String generalManager, String headCoach, String playerName, String position, boolean captain) throws SQLException;
	
}
