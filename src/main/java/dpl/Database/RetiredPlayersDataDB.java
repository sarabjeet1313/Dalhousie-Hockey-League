package dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import dpl.DplConstants.LeagueConstants;
import dpl.DplConstants.StoredProcedureConstants;
import dpl.TeamManagement.IRetiredPlayerPersistance;
import dpl.TeamManagement.League;
import dpl.TeamManagement.Player;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

public class RetiredPlayersDataDB implements IRetiredPlayerPersistance {
    InvokeStoredProcedure invoke = null;
    IUserOutput output = new CmdUserOutput();

    @Override
    public boolean persistRetiredPlayers(Player player, String teamName, League league) {
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