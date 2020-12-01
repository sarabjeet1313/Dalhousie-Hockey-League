package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamPointObserver implements IObserver {
	private Map<String, Integer> teamPoints;

	public TeamPointObserver() {
		teamPoints = new HashMap<>();
	}

	@Override
	public void update(Subject subject) {
		String teamWithHighPoints = null;
		int points = 0;
		Team team = (Team) subject.getValue(TrophySystemConstants.TEAM.toString());
		if (teamPoints.containsKey(team.getTeamName())) {
			teamPoints.put(team.getTeamName(), teamPoints.get(team.getTeamName()) + 1);
		} else {
			teamPoints.put(team.getTeamName(), 1);
		}
		for (String teamName : teamPoints.keySet()) {
			if (points < teamPoints.get(teamName)) {
				teamWithHighPoints = teamName;
			}
		}
		TeamPoint.getInstance().setBestTeam(teamWithHighPoints);
	}

}
