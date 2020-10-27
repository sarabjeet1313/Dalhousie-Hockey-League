package dal.asd.dpl.TeamManagement;

import java.util.List;

public interface IRetirementManager {
	
	public int getLikelihoodOfRetirement(League league, Player player);
	
	public boolean shouldPlayerRetire(League league, Player player);
	
	public League replaceRetiredPlayers(League league);
	
	public Player updateRetiredPlayers(Player player, List<Player> freeAgentsList);
	
}
