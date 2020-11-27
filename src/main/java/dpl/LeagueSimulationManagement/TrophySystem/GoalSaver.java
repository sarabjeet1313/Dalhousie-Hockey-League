package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class GoalSaver extends Subject{
    private static GoalSaver instance;
    private Player bestGoalSaver;

    private GoalSaver(){ }

    public static GoalSaver getInstance(){
        if(instance == null){
            instance = new GoalSaver();
        }
        return  instance;
    }

    public void notifyWhenPlayerSaveGoal(Player player){
        setValue("player", player);
        notifyAllObservers();
    }

    public Player getBestGoalSaver() {
        return bestGoalSaver;
    }

    public void setBestGoalSaver(Player bestGoalSaver) {
        this.bestGoalSaver = bestGoalSaver;
    }
}
