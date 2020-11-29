package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public class ParticipantAward extends Subject {
    private static ParticipantAward instance;
    private String teamWithLowestPoints;

    private ParticipantAward() {
    }

    public static ParticipantAward getInstance() {
        if (instance == null) {
            instance = new ParticipantAward();
        }
        return instance;
    }

    public void notifyParticipatedTeam(Team team) {
        setValue(TrophySystemConstants.TEAM.toString(), team);
        notifyAllObservers();
    }

    public String getTeamWithLowestPoints() {
        return teamWithLowestPoints;
    }

    public void setTeamWithLowestPoints(String teamWithLowestPoints) {
        this.teamWithLowestPoints = teamWithLowestPoints;
    }
}
