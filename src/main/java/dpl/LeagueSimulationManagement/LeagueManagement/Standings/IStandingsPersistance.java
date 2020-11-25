package dpl.LeagueSimulationManagement.LeagueManagement.Standings;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IStandingsPersistance {

	void setSeason(int season);

	boolean insertToStandings(Standing standing) throws IOException;

//	void updateStandingsLosses(String teamName) throws SQLException;
//
//	void updateStandingsWin(String teamName) throws SQLException;
//
//	public List<String>  getTop4TeamsFromStandings(String divisionName) throws SQLException;

}
