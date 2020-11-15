package dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement;

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
            if (p.getPosition() == "forward"){
                forward--;
            }
            if(p.getPosition() == "defence"){
                defence--;
            }
            if(p.getPosition() == "goalie"){
                goalie--;
            }
        }

        if(forward == 0 || defence == 0 || goalie == 0){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}

