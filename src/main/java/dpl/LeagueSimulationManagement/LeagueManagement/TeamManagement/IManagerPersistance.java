package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.sql.SQLException;

public interface IManagerPersistance {

	public boolean persistManagerInfo(String managerName, String teamName, String leagueName) throws SQLException, IOException;

}
