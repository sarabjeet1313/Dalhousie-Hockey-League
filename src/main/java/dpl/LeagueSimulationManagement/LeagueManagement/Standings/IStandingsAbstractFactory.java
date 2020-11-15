package dpl.LeagueSimulationManagement.LeagueManagement.Standings;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public interface IStandingsAbstractFactory {

	public StandingInfo StandingInfo(League leagueToSimulate, int season, IStandingsPersistance standingsDb);

}
