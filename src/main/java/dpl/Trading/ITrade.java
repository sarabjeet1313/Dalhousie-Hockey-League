package dpl.Trading;

import java.sql.SQLException;

import dpl.TeamManagement.League;

public interface ITrade {

	public League startTrade(League leagueObject) throws SQLException;

}
