package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dpl.DplConstants.LeagueConstants;
import dpl.DplConstants.StoredProcedureConstants;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.IRetiredPlayerPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Player;

public class RetiredPlayersDataDB implements IRetiredPlayerPersistance {
	InvokeStoredProcedure invoke = null;

	@Override
	public boolean persistRetiredPlayers(Player player, String teamName, League league) throws SQLException {
		boolean isPersisted = Boolean.FALSE;
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureConstants.UPDATE_RETIRED_PLAYERS_IN_LEAGUE.getSpString());
			invoke.setParameter(1, league.getLeagueName());
			invoke.setParameter(2, teamName);
			invoke.setParameter(3, player.getPlayerName());
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(LeagueConstants.SUCCESS.toString());
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			invoke.closeConnection();
		}
		return isPersisted;
	}

}