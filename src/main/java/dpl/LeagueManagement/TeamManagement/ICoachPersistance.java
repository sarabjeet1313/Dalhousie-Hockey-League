package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;

public interface ICoachPersistance {

	public boolean persistCoaches(Coach coach, String teamName, String leagueName) throws IOException;

}
