package dpl.LeagueManagement.TeamManagement;

import java.util.List;
import java.util.Map;

public interface IAllStarGameManagement {
	
	public List<Team> performAllStarGame(League league);
	
	public Map<String, List<Player>> getSortedPlayersByType(List<Conference> conferenceList, String PlayerType);
	
	public List<Map<Player, String>> getPlayersBytTeam();

}
