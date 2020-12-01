package dpl.LeagueManagement.Trading;

import java.util.List;

import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Team;

public interface ITrade {

	public League startTrade(League leagueObject, StandingInfo standingInfo);
	public List<Team> startTradeDraftPick(League league, List<Team> teamList);
}
