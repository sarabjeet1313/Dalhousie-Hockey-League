package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import dpl.DplConstants.PlayerConstants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RosterManagement implements IRosterManagement{
    // get ITeamInfo instance
    ITeamInfo teamInfo = new Team();

    public RosterManagement(){
        super();
    }

    @Override
    public boolean checkRoster(String teamName, League league) {
        int forward = 16;
        int defence = 10;
        int goalie = 4;
        List<Player> tempPlayerList = teamInfo.getPlayersByTeam(teamName,league);

        for (Player p: tempPlayerList){
            if (p.getPosition().equals("forward")){
                forward--;
            }
            if(p.getPosition().equals("defence")){
                defence--;
            }
            if(p.getPosition().equals("goalie")){
                goalie--;
            }
        }

        if(forward == 0 || defence == 0 || goalie == 0){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public boolean updateActiveStatus(String teamName, League league) {

        List<Player> playerList = teamInfo.getPlayersByTeam(teamName,league);
        List<Player> updatedPlayerList = new ArrayList<>();
        List<Player> goalieList= new ArrayList<>();
        List<Player> nonInjuredList = new ArrayList<>();
        List<Player> injuredList = new ArrayList<>();

        for(int pIndex = 0; pIndex < playerList.size(); pIndex++){
            Player tempPlayer = playerList.get(pIndex);
            if(tempPlayer.getDaysInjured() <= 0){
                if(tempPlayer.getPosition().equals("goalie")){
                    goalieList.add(tempPlayer);
                }else{
                    nonInjuredList.add(tempPlayer);
                }
            }else{
                if(tempPlayer.getPosition().equals("goalie")){
                    goalieList.add(tempPlayer);
                }else{
                    injuredList.add(tempPlayer);
                }
            }
        }
        goalieList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));
        nonInjuredList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));
        injuredList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));

        Collections.reverse(goalieList);
        Collections.reverse(nonInjuredList);
        Collections.reverse(injuredList);

        for (int index = 0 ; index< goalieList.size(); index++){
            if(index < 2){
                goalieList.get(index).setIsActive(Boolean.TRUE);
            }else{
                goalieList.get(index).setIsActive(Boolean.FALSE);
            }
        }

        if(nonInjuredList.size()> 18){
            for(int index = 0; index < nonInjuredList.size(); index++ ){
                if(index < 18){
                    nonInjuredList.get(index).setIsActive(Boolean.TRUE);
                }else {
                    nonInjuredList.get(index).setIsActive(Boolean.FALSE);
                    for (int indexInjured = 0; indexInjured< injuredList.size(); indexInjured++){
                        injuredList.get(indexInjured).setIsActive(Boolean.FALSE);
                    }
                }
            }
        }else{
            for(int index = 0; index < nonInjuredList.size(); index++ ){
                nonInjuredList.get(index).setIsActive(Boolean.TRUE);
            }
            int tempCount = 18 - nonInjuredList.size();
            for (int indexCount = 0; indexCount < injuredList.size() ; indexCount++){
                if(indexCount< tempCount){
                    injuredList.get(indexCount).setIsActive(Boolean.TRUE);
                }else{
                    injuredList.get(indexCount).setIsActive(Boolean.FALSE);
                }

            }
        }
        updatedPlayerList.addAll(goalieList);
        updatedPlayerList.addAll(nonInjuredList);
        updatedPlayerList.addAll(injuredList);

        teamInfo.setPlayersByTeam(teamName,updatedPlayerList, league);

        return true;
    }

    @Override
    public League updateLeagueActiveStatus(League league) {
        String currentTeamName;
        List<Conference> conferenceList = league.getConferenceList();
        for (int index = 0; index < conferenceList.size(); index++) {
            List<Division> divisionList = conferenceList.get(index).getDivisionList();
            for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                List<Team> teamList = divisionList.get(dIndex).getTeamList();
                for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
                    currentTeamName = teamList.get(tIndex).getTeamName();
                    updateActiveStatus(currentTeamName, league);
                }
            }
        }
        return league;
    }
}

