package dpl.LeagueSimulationManagement.TrophySystem;

import java.util.Map;

public interface ISubscriber {
    void update(Map<String, Object> dataMap);
}
