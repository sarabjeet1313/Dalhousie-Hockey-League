package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

import java.util.List;

public interface ITradePersistence {

    public List<String> getEligibleTeamName(int lossPoints, League league, StandingInfo standing);

    public boolean resetTradeLossPoint(String teamName, StandingInfo standingInfo);
}
