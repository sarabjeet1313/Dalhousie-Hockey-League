package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;

import java.util.*;

public class BestCoachLeagueObserver implements IObserver{
    private Map<Coach, Integer> coachs;

    public BestCoachLeagueObserver(){
        coachs = new HashMap<>();
    }
    @Override
    public void update(Subject subject) {
        Coach coach = (Coach) subject.getValue("Coach");
        int statPlayer = (Integer) subject.getValue("StatPlayer");
        if(coachs.containsKey(coach)){
            coachs.put(coach, coachs.get(coach) + 1);
        }else{
            coachs.put(coach, statPlayer);
        }
        Coach bestCoach = null;
        int statCount = 0;
        Set<Coach> keys = coachs.keySet();
        for(Coach key: keys){
            if(statCount < coachs.get(key)){
                statCount = coachs.get(key);
                bestCoach = key;
            }
        }

        BestCoachLeague.getInstance().setBestCoach(bestCoach);
    }
}
