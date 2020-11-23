package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.sql.SQLException;

import dpl.ErrorHandling.RetirementManagementException;

public interface IRetirementManagement {

    public int getLikelihoodOfRetirement(League league, Player player);

    public boolean shouldPlayerRetire(League league, Player player) throws SQLException;

    public League replaceRetiredPlayers(League league) throws SQLException, RetirementManagementException, IOException;

    public League increaseAge(int days, League league) throws SQLException, RetirementManagementException, IOException;

}
