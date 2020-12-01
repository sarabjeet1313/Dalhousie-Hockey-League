package dpl.LeagueSimulationManagement.LeagueManagement.Standings;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class StandingsAbstractFactory implements IStandingsAbstractFactory {

	public StandingInfo StandingInfo(League leagueToSimulate, int season, IStandingsPersistance standingsDb, IUserOutput output) {
		return new StandingInfo(leagueToSimulate, season, standingsDb, output);
	}

	public Standing Standing(){
		return new Standing();
	}

	public TeamStanding TeamStanding(){
		return new TeamStanding();
	}

}
