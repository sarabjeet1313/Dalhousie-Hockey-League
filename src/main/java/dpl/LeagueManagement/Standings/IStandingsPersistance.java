package dpl.LeagueManagement.Standings;

import java.io.IOException;

public interface IStandingsPersistance {

	void setSeason(int season);

	boolean insertToStandings(Standing standing) throws IOException;

}
