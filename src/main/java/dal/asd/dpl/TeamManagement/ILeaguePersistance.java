package dal.asd.dpl.TeamManagement;

public interface ILeaguePersistance {

	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, Player player);

	public int checkLeagueName(String leagueName);

	public League loadLeagueData(String teamName);

	public boolean persisitRetiredPlayers(Player player, String teamName, League league);

}
