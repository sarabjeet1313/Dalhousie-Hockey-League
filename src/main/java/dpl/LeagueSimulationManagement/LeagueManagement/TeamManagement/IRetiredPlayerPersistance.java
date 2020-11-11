package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.sql.SQLException;

public interface IRetiredPlayerPersistance {

	public void persistRetiredPlayers(Player player, String teamName, League league) throws SQLException;

}
