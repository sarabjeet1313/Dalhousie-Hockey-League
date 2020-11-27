package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public class TeamPoints extends Subject{
    private static TeamPoints instance;
    private String bestTeam;

    private TeamPoints(){ }

    public static TeamPoints getInstance(){
        if(instance == null){
            instance = new TeamPoints();
        }
        return  instance;
    }
    public void notifyWhenATeamWinsTheMatch(Team team){
        setValue("team", team);
        notifyAllObservers();
    }

    public String getBestTeam() {
        return bestTeam;
    }

    public void setBestTeam(String bestTeam) {
        this.bestTeam = bestTeam;
    }
}
