package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class TopGoalScorer extends Subject{
    private static TopGoalScorer instance;
    private Player topGoalScorer;

    private TopGoalScorer(){ }

    public static TopGoalScorer getInstance(){
        if(instance == null){
            instance = new TopGoalScorer();
        }
        return  instance;
    }

    public void notifyWhenPlayerGoal(Player player){
        setValue("player", player);
        notifyAllObservers();
    }

    public Player getTopGoalScorer() {
        return topGoalScorer;
    }

    public void setTopGoalScorer(Player topGoalScorer) {
        this.topGoalScorer = topGoalScorer;
    }
}
