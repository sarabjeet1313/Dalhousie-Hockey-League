package dpl.TeamManagement;

public interface IRetiredPlayerPersistance {
	
	public boolean persistRetiredPlayers(Player player, String teamName, League league);
	
}
