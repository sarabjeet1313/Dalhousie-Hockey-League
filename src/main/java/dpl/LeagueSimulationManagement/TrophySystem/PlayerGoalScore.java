package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class PlayerGoalScore extends Subject {
    private static PlayerGoalScore instance;
    private Player bestPlayer;

    private PlayerGoalScore() {
    }

    public static PlayerGoalScore getInstance() {
        if (instance == null) {
            instance = new PlayerGoalScore();
        }
        return instance;
    }

    public void notifyWhenPlayerGoal(Player player) {
        setValue(TrophySystemConstants.PLAYER.toString(), player);
        notifyAllObservers();
    }

    public Player getBestPlayer() {
        return bestPlayer;
    }

    public void setBestPlayer(Player bestPlayer) {
        this.bestPlayer = bestPlayer;
    }
}
