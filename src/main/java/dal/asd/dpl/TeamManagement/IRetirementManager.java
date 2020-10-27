package dal.asd.dpl.TeamManagement;

public interface IRetirementManager {
	
	public int getLikelihoodOfRetirement(League league, Player player);
	
	public boolean shouldPlayerRetire(League league, Player player);
	
	public League replaceRetiredPlayers(League league);
	
}
