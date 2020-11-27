package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public class ParticipantsAward extends Subject{
    private static ParticipantsAward instance;
    private String teamWithLowestPoints;

    private ParticipantsAward(){ }

    public static ParticipantsAward getInstance(){
        if(instance == null){
            instance = new ParticipantsAward();
        }
        return  instance;
    }

    public void notifyParticipatedTeam(Team team){
        setValue("team", team);
        notifyAllObservers();
    }

    public String getTeamWithLowestPoints() {
        return teamWithLowestPoints;
    }

    public void setTeamWithLowestPoints(String teamWithLowestPoints) {
        this.teamWithLowestPoints = teamWithLowestPoints;
    }
}
