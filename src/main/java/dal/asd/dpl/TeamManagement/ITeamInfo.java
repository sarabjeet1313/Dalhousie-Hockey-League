package dal.asd.dpl.TeamManagement;

import java.util.List;

public interface ITeamInfo {
	
	public boolean isValidTeamName(String conferenceName, String divisionName, String teamName, League league);

	public double getTeamStrength(String teamName, League league);

	public boolean shouldReverseResult(double randomChance);
	
	public List<Player> getPlayersByTeam(String teamName, League league);

}
