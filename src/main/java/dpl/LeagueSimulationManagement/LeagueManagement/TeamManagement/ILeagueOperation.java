package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ILeagueOperation {

	public League loadLeague(String teamName) throws SQLException, IOException;

	public boolean createTeam(League league) throws SQLException, IOException;

	public League loadLeagueObject(String leagueName, String conferenceName, String divisionName, Team team,
			League league) throws NullPointerException;

	public List<List<Player>> getAvailableLeaguePlayers(League league);

	public boolean updateLeague(League league) throws SQLException, IOException;
}
