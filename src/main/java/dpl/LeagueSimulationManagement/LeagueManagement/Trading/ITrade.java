package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.sql.SQLException;
import java.util.List;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.Standing;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public interface ITrade {

	public League startTrade(League leagueObject, StandingInfo standingInfo);
	public List<Team> startTradeDraftPick(League league, List<Team> teamList);
}
