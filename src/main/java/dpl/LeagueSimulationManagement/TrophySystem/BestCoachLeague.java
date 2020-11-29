package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;

public class BestCoachLeague extends Subject {
    private Coach bestCoach;
    private static BestCoachLeague instance;

    private BestCoachLeague() {
    }

    public static BestCoachLeague getInstance() {
        if (instance == null) {
            instance = new BestCoachLeague();
        }
        return instance;
    }

    public void notifyCoachTraining(Coach coach, int statPlayer) {
        setValue(TrophySystemConstants.COACH.toString(), coach);
        setValue(TrophySystemConstants.STAT_PLAYER.toString(), statPlayer);
        notifyAllObservers();
    }

    public void setBestCoach(Coach coach) {
        this.bestCoach = coach;
    }

    public Coach getBestCoach() {
        return this.bestCoach;
    }
}
