package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipantAwardObserver implements IObserver {
    private Map<String, Integer> teamPoints;

    public ParticipantAwardObserver() {
        teamPoints = new HashMap<>();
    }

    @Override
    public void update(Subject subject) {
        String teamWithLowPoints = null;
        int points = 0;
        Team team = (Team) subject.getValue(TrophySystemConstants.TEAM.toString());
        if (teamPoints.containsKey(team.getTeamName())) {
            teamPoints.put(team.getTeamName(), teamPoints.get(team.getTeamName()) + 1);
        } else {
            teamPoints.put(team.getTeamName(), 1);
        }
        if(teamPoints.size() > 0){
            teamWithLowPoints = (String) teamPoints.keySet().toArray()[0];
            points = teamPoints.get(teamWithLowPoints);
        }
        if(teamPoints.size() > 1) {
            for (String teamName : teamPoints.keySet()) {
                if (points > teamPoints.get(teamName)) {
                    teamWithLowPoints = teamName;
                }
                points = teamPoints.get(teamName);
            }
        }

        ParticipantAward.getInstance().setTeamWithLowestPoints(teamWithLowPoints);
    }
}
