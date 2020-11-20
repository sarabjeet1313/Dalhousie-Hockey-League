package dpl.LeagueSimulationManagement.LeagueManagement.Standings;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public interface IStandingsAbstractFactory {

	public StandingInfo StandingInfo(League leagueToSimulate, int season, IStandingsPersistance standingsDb, IUserOutput output);

	public Standing Standing();

	public TeamStanding TeamStanding();
}
