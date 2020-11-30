package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.io.IOException;

public interface IManagerPersistance {

	public boolean persistManagerInfo(String managerName, String teamName, String leagueName) throws IOException;

}
