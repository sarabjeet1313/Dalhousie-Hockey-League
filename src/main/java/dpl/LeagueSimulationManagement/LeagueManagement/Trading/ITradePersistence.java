package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

import java.util.List;

public interface ITradePersistence {

    public List<String> getEligibleTeamName(int lossPoints, League league);

    public boolean resetTradeLossPoint(String teamName);
}
