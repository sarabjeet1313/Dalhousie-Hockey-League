package dal.asd.dpl.TeamManagement;

import java.util.List;

public interface ITeamPlayersInfo {
		
	public List<Player> getPlayersByTeam(String teamName, Leagues league);

}
