package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.util.List;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public interface IPlayerDraft {

	public List<Team> generateDraftingTeams(List<String> teamList, League league);
	
	public List<Player> generateDraftingPlayers(int teamCount);
	
	public List<Team> startRoundDraft(List<Team> teamList, List<Player> playerList);
	
	public League postDrafting(List<Team> teamList, League league);
}
