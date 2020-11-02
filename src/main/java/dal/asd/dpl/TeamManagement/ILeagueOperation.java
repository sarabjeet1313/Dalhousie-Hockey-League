package dal.asd.dpl.TeamManagement;

import java.util.List;

public interface ILeagueOperation {
	
	public League loadLeague(String teamName);
	
	public boolean createTeam(League league);
	
	public League loadLeagueObject(String leagueName, String conferenceName, String divisionName, Team team, League league);
	
	public List<List<Player>> getAvailableLeaguePlayers(League league);
	
	public boolean UpdateLeague(League league);
}
