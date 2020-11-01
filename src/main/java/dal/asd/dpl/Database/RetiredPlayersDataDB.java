package dal.asd.dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.asd.dpl.TeamManagement.IRetiredPlayerPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.LeagueUtil;
import dal.asd.dpl.Util.StoredProcedureUtil;

public class RetiredPlayersDataDB implements IRetiredPlayerPersistance{
	
	InvokeStoredProcedure invoke = null;
	IUserOutput output = new CmdUserOutput();

	@Override
	public boolean persisitRetiredPlayers(Player player, String teamName, League league) {
		boolean isPersisted = Boolean.FALSE;
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.UPDATE_RETIRED_PLAYERS_IN_LEAGUE.getSpString());
			invoke.setParameter(1, league.getLeagueName());
			invoke.setParameter(2, teamName);
			invoke.setParameter(3, player.getPlayerName());
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(LeagueUtil.SUCCESS.toString());
			}
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				output.setOutput(e.getMessage());
				output.sendOutput();
			}
		}
		return isPersisted;
	}

}
