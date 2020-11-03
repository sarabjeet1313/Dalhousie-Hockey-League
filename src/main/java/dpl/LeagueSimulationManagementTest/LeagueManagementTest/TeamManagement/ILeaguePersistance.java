package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement;

import java.sql.SQLException;

public interface ILeaguePersistance {

    public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName,
                                      String generalManager, String headCoach, Player player) throws SQLException;

    public int checkLeagueName(String leagueName) throws SQLException;

    public League loadLeagueData(String teamName) throws SQLException;

    public boolean UpdateLeagueData(String leagueName, String teamName, Player player) throws SQLException;

}
