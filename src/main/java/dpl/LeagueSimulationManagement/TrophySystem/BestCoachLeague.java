package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public class BestCoachLeague extends Subject{
    private Coach bestCoach;
    private static BestCoachLeague instance;

    private BestCoachLeague(){ }

    public static BestCoachLeague getInstance(){
        if(instance == null){
            instance = new BestCoachLeague();
        }
        return  instance;
    }

    public void notifyCoachTraining(Coach coach, int statPlayer){
        setValue("Coach", coach);
        setValue("StatPlayer", statPlayer);
        notifyAllObservers();
    }

    public void setBestCoach(Coach coach){
        this.bestCoach = coach;
    }

    public Coach getBestCoach(){
        return this.bestCoach;
    }
}
