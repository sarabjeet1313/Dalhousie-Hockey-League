package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Team;

public class TeamPoint extends Subject {
	private static TeamPoint instance;
	private String bestTeam;

	private TeamPoint() {
	}

	public static TeamPoint getInstance() {
		if (instance == null) {
			instance = new TeamPoint();
		}
		return instance;
	}

	public void notifyTeamWinsTheMatch(Team team) {
		setValue(TrophySystemConstants.TEAM.toString(), team);
		notifyAllObservers();
	}

	public String getBestTeam() {
		return bestTeam;
	}

	public void setBestTeam(String bestTeam) {
		this.bestTeam = bestTeam;
	}
}
