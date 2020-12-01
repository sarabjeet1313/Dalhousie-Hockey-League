package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;

public interface IRetiredPlayerPersistance {

	public void persistRetiredPlayers(Player player, String teamName, League league) throws IOException;

}
