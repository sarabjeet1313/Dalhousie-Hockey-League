package dpl.LeagueSimulationManagementTest.LeagueManagementTest.Trading;

import java.sql.SQLException;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;

public interface ITrade {

	public League startTrade(League leagueObject) throws SQLException;

}
