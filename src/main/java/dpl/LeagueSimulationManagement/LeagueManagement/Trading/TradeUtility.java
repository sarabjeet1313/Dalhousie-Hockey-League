package dpl.LeagueSimulationManagement.LeagueManagement.Trading;

import java.util.ArrayList;
import java.util.List;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.Standing;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.TeamStanding;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;

import dpl.SystemConfig;

public class TradeUtility implements ITradePersistence{

    ITeamManagementAbstractFactory teamManagementAbstractFactory = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
    ITeamInfo teamInfo = teamManagementAbstractFactory.Team();

    public List<String> getEligibleTeamName(int lossPoint, League league, StandingInfo standingInfo){
        List<String> eligibleTeamNames = new ArrayList<>();
//        List<String> allTeamNames = teamInfo.getAllTeamName(league);
        Standing standing = standingInfo.getStanding();
        List<TeamStanding> teamStandingList = standing.getStandings();

        for(TeamStanding teamStanding: teamStandingList){
            if (teamStanding.getTradeLossPoint() >= lossPoint ){
                eligibleTeamNames.add(teamStanding.getTeamName());
            }

        }
        return eligibleTeamNames;
    }

    public boolean resetTradeLossPoint(String teamName, StandingInfo standingInfo){
        boolean isPersisted = Boolean.FALSE;
        Standing standing = standingInfo.getStanding();
        List<TeamStanding> teamStandingList = standing.getStandings();

        for(TeamStanding teamStanding: teamStandingList){
            if(teamStanding.getTeamName().equals(teamName)){
                teamStanding.setTradeLossPoint(0);
                isPersisted = Boolean.TRUE;
            }
        }
        return isPersisted;
    }

}
