package dpl.TeamManagement;

import java.sql.SQLException;

public interface IRetiredPlayerPersistance {

	public boolean persistRetiredPlayers(Player player, String teamName, League league) throws SQLException;

}
