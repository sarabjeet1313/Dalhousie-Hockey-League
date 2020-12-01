package dpl.LeagueManagement.TeamManagement;

public interface IRosterManagement {

	public boolean checkRoster(String teamName, League league);
	public boolean updateActiveStatus(String teamName, League league);
	public League updateLeagueActiveStatus(League league);
	public League balanceOutRoster(League league);
	public boolean balanceOutSingleRoster(String teamName, League league);

}
