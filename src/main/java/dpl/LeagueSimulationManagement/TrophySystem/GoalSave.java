package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class GoalSave extends Subject {
    private static GoalSave instance;
    private Player bestGoalSaver;

    private GoalSave() {
    }

    public static GoalSave getInstance() {
        if (instance == null) {
            instance = new GoalSave();
        }
        return instance;
    }

    public void notifyPlayerSaveGoal(Player player) {
        setValue(TrophySystemConstants.PLAYER.toString(), player);
        notifyAllObservers();
    }

    public Player getBestGoalSaver() {
        return bestGoalSaver;
    }

    public void setBestGoalSaver(Player bestGoalSaver) {
        this.bestGoalSaver = bestGoalSaver;
    }
}
