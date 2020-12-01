package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;

public interface IManagerPersistance {

	public boolean persistManagerInfo(Manager manager, String teamName, String leagueName) throws IOException;

}
