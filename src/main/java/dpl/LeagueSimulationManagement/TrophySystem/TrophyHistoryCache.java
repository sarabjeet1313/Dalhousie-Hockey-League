package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

import java.util.ArrayList;
import java.util.List;

public class TrophyHistoryCache {
    private static List<TrophyState> trophyList = new ArrayList<>();
    private static TrophyHistoryCache instance;

    private TrophyHistoryCache() {
    }

    public static TrophyHistoryCache getInstance() {
        if (instance == null) {
            instance = new TrophyHistoryCache();
        }
        return instance;
    }

    public void add(TrophyState trophy) {
        instance.add(trophy);
    }

    public List<TrophyState> getTrophies() {
        return new ArrayList<TrophyState>(trophyList);
    }

    public void print() {

    }

}
