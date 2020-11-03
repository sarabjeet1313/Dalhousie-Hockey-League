package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement;

import java.sql.SQLException;

public interface IRetirementManagement {

    public int getLikelihoodOfRetirement(League league, Player player);

    public boolean shouldPlayerRetire(League league, Player player) throws SQLException;

    public League replaceRetiredPlayers(League league) throws SQLException;

    public League increaseAge(int days, League league) throws SQLException;

}
