package dpl.Database;

import java.util.ArrayList;
import java.util.List;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.Standing;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.TeamStanding;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.SystemConfig;

public class TradeDataDB implements ITradePersistence {
    IStandingsAbstractFactory standingsAbstractFactory = SystemConfig.getSingleInstance().getStandingsAbstractFactory();
    Standing standing = standingsAbstractFactory.Standing();

    ITeamManagementAbstractFactory teamManagementAbstractFactory = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
    ITeamInfo teamInfo = teamManagementAbstractFactory.Team();
    League league = teamManagementAbstractFactory.League();

    @Override
    public List<String> getEligibleTeamName(int lossPoint){
        List<String> eligibleTeamNames = new ArrayList<>();
        List<String> allTeamNames = teamInfo.getAllTeamName(league);
        List<TeamStanding> teamStandingList = standing.getStandings();

        for(String teamName: allTeamNames){
            for(TeamStanding teamStanding: teamStandingList){
                if(teamStanding.getTeamName().equals(teamName)){
                    if (teamStanding.getTradeLossPoint() >= lossPoint ){
                        eligibleTeamNames.add(teamName);
                    }
                }
            }
        }
        return eligibleTeamNames;
    }

    @Override
    public boolean resetTradeLossPoint(String teamName){
        boolean isPersisted = Boolean.FALSE;
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
