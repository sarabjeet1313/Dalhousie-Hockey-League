package dal.asd.dpl.TeamManagement;

import java.sql.SQLException;
import java.util.List;

public interface ILeague {
	
	public List<Leagues> getLeagueData(String teamName) throws SQLException;
	
	public int checkLeagueName(String leagueName) throws SQLException;
	
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName, 
			String generalManager, Coach headCoach, String playerName, String position, boolean captain, int age, int skating, int shooting, int checking, int saving) throws SQLException;
	
}
