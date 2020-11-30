package dpl.LeagueSimulationManagement.LeagueManagement.Standings;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IStandingsPersistance {

	void setSeason(int season);

	boolean insertToStandings(Standing standing) throws IOException;

}
