package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;

import java.util.*;

public class BestCoachLeagueObserver implements IObserver {
    private Map<Coach, Integer> coach;

    public BestCoachLeagueObserver() {
        coach = new HashMap<>();
    }

    @Override
    public void update(Subject subject) {
        Coach coach = (Coach) subject.getValue(TrophySystemConstants.COACH.toString());
        int statPlayer = (Integer) subject.getValue(TrophySystemConstants.STAT_PLAYER.toString());
        if (this.coach.containsKey(coach)) {
            this.coach.put(coach, this.coach.get(coach) + 1);
        } else {
            this.coach.put(coach, statPlayer);
        }
        Coach bestCoach = null;
        int statCount = 0;
        Set<Coach> keys = this.coach.keySet();
        for (Coach key : keys) {
            if (statCount < this.coach.get(key)) {
                statCount = this.coach.get(key);
                bestCoach = key;
            }
        }

        BestCoachLeague.getInstance().setBestCoach(bestCoach);
    }
}
