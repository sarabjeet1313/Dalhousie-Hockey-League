package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.util.ArrayList;
import java.util.List;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.Standing;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.TeamStanding;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

public class TradeUtility implements ITradePersistence {

    public List<String> getEligibleTeamName(int lossPoint, League league, StandingInfo standingInfo) {
        List<String> eligibleTeamNames = new ArrayList<>();
        Standing standing = standingInfo.getStanding();
        List<TeamStanding> teamStandingList = standing.getStandings();

        for (TeamStanding teamStanding : teamStandingList) {
            if (teamStanding.getTradeLossPoint() >= lossPoint) {
                eligibleTeamNames.add(teamStanding.getTeamName());
            }

        }
        return eligibleTeamNames;
    }

    public boolean resetTradeLossPoint(String teamName, StandingInfo standingInfo) {
        boolean isPersisted = Boolean.FALSE;
        Standing standing = standingInfo.getStanding();
        List<TeamStanding> teamStandingList = standing.getStandings();

        for (TeamStanding teamStanding : teamStandingList) {
            if (teamStanding.getTeamName().equals(teamName)) {
                teamStanding.setTradeLossPoint(0);
                isPersisted = Boolean.TRUE;
            }
        }
        return isPersisted;
    }

}
