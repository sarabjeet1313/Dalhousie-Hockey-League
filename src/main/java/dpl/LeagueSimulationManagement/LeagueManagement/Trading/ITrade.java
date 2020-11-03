package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.sql.SQLException;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public interface ITrade {

	public League startTrade(League leagueObject) throws SQLException;

}
