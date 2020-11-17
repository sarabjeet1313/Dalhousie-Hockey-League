package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RosterManagement implements IRosterManagement{
    // get ITeamInfo instance
    ITeamInfo teamInfo = new Team();

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
        List<Player> nonInjuredList = new LinkedList<>();
        List<Player> injuredList = new LinkedList<>();

        for(int pIndex = 0; pIndex < playerList.size(); pIndex++){
            Player tempPlayer = playerList.get(pIndex);
            if(tempPlayer.getDaysInjured() <= 0){
                nonInjuredList.add(tempPlayer);
            }else{
                injuredList.add(tempPlayer);
            }
        }
        nonInjuredList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));
        injuredList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));
        // need goalie
        if(nonInjuredList.size()>=20){
            for(int index = 0; index < nonInjuredList.size(); index++ ){
                if(index < 20){
                    nonInjuredList.get(index).setIsActive(Boolean.TRUE);
                }else {
                    nonInjuredList.get(index).setIsActive(Boolean.FALSE);
                }
            }
        }else{

        }

        return false;
    }
}

