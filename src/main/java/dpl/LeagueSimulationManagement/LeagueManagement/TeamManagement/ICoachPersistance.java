package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.sql.SQLException;

public interface ICoachPersistance {

    public boolean persistCoaches(Coach coach, String teamName, String leagueName) throws SQLException, IOException;

}
