package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement;

import java.sql.SQLException;

public interface ICoachPersistance {

    public boolean persistCoaches(Coach coach, String teamName, String leagueName) throws SQLException;

}
