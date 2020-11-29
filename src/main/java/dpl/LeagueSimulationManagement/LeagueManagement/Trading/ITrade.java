package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.sql.SQLException;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.Standing;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public interface ITrade {

	public League startTrade(League leagueObject, StandingInfo standingInfo) throws SQLException;

}
