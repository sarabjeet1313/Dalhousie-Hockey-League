package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.util.List;

public interface ILeagueOperation {

	public League loadLeague(String teamName) throws IOException;

	public boolean createTeam(League league) throws IOException;

	public League loadLeagueObject(String leagueName, String conferenceName, String divisionName, Team team,
			League league) throws NullPointerException;

	public List<List<Player>> getAvailableLeaguePlayers(League league);

	public boolean updateLeague(League league) throws IOException;
}
