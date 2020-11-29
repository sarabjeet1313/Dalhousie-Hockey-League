package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class TopGoalScore extends Subject {
    private static TopGoalScore instance;
    private Player topGoalScorer;

    private TopGoalScore() {
    }

    public static TopGoalScore getInstance() {
        if (instance == null) {
            instance = new TopGoalScore();
        }
        return instance;
    }

    public void notifyPlayerGoal(Player player) {
        setValue(TrophySystemConstants.PLAYER.toString(), player);
        notifyAllObservers();
    }

    public Player getTopGoalScore() {
        return topGoalScorer;
    }

    public void setTopGoalScore(Player topGoalScorer) {
        this.topGoalScorer = topGoalScorer;
    }
}
