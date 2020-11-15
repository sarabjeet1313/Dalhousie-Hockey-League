package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.sql.SQLException;

import dpl.Dpl.ErrorHandling.RetirementManagementException;

public interface IRetirementManagement {

    public int getLikelihoodOfRetirement(League league, Player player);

    public boolean shouldPlayerRetire(League league, Player player) throws SQLException;

    public League replaceRetiredPlayers(League league) throws SQLException, RetirementManagementException;

    public League increaseAge(int days, League league) throws SQLException, RetirementManagementException;

}
