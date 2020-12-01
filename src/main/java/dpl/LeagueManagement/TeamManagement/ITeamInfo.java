package dpl.LeagueManagement.TeamManagement;

import java.util.List;

public interface ITeamInfo {

	public boolean isValidTeamName(String conferenceName, String divisionName, String teamName, League league);

	public double getTeamStrength(String teamName, League league);

	List<Player> getActivePlayers(String teamName, League league);

	public boolean shouldReverseResult(double randomChance);

	public List<Player> getPlayersByTeam(String teamName, League league);

	public void setPlayersByTeam(String teamName, List<Player> updatedPlayerList, League league);

	public List<String> getAllTeamName(League league);

	public String getUserTeamName(League league);

}
