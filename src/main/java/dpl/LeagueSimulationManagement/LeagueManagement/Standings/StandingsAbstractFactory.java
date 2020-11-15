package dpl.LeagueSimulationManagement.LeagueManagement.Standings;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public class StandingsAbstractFactory implements IStandingsAbstractFactory {

	public StandingInfo StandingInfo(League leagueToSimulate, int season, IStandingsPersistance standingsDb) {
		return new StandingInfo(leagueToSimulate, season, standingsDb);
	}

}
