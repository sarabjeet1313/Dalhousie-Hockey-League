package dpl.LeagueManagement.Standings;

import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public interface IStandingsAbstractFactory {

	StandingInfo StandingInfo(League leagueToSimulate, int season, IStandingsPersistance standingsDb, IUserOutput output);

	Standing Standing();

	TeamStanding TeamStanding();
}
