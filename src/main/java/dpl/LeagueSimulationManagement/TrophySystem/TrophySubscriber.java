package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TrophyState;

import java.util.HashMap;
import java.util.Map;

public class TrophySubscriber implements IPresidentsTrophy, ICalderMemorialTrophy, IJackAdamsAward, IMauriceRichardTrophy, IParticipationAward, IRobHawkeyMemorialCup, IVezinaTrophy {

    Map<String, Integer> scoreTable = new HashMap<>();

    @Override
    public void updateTrophy(String teamWin, String teamLose) {

        if (scoreTable.containsKey(teamWin)) {
            scoreTable.put(teamWin, scoreTable.get(teamWin) + 1);
        } else {
            scoreTable.put(teamWin, 1);
        }

        if (scoreTable.containsKey(teamLose)) {
            scoreTable.put(teamLose, scoreTable.get(teamLose) + 0);
        } else {
            scoreTable.put(teamLose, 0);
        }

    }

    @Override
    public void updateTrophy(TrophyState trophy) {

    }
}
