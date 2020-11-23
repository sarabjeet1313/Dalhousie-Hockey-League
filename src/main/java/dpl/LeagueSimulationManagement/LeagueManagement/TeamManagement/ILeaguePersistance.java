package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.sql.SQLException;

public interface ILeaguePersistance {

    public boolean persisitLeagueData(League league, String conferenceName, String divisionName, String teamName,
                                      String generalManager, String headCoach, Player player) throws SQLException, IOException;

    public int checkLeagueName(League league) throws SQLException, IOException;

    public League loadLeagueData(String teamName) throws SQLException, IOException;

    public boolean updateLeagueData(League league, String teamName, Player player) throws SQLException, IOException;

}
