package dpl.Database;

import java.sql.SQLException;

import dpl.DplConstants.StoredProcedureConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRetiredPlayerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class RetiredPlayersDataDB implements IRetiredPlayerPersistance {
	InvokeStoredProcedure invoke = null;

	@Override
	public void persistRetiredPlayers(Player player, String teamName, League league) throws SQLException {
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureConstants.UPDATE_RETIRED_PLAYERS_IN_LEAGUE.getSpString());
			invoke.setParameter(1, league.getLeagueName());
			invoke.setParameter(2, teamName);
			invoke.setParameter(3, player.getPlayerName());
			invoke.executeQueryWithResults();
		} catch (SQLException e) {
			throw e;
		} finally {
			invoke.closeConnection();
		}
	}

}