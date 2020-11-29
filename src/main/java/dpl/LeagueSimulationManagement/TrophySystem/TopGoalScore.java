package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class TopGoalScore extends Subject{
    private static TopGoalScore instance;
    private Player topGoalScorer;

    private TopGoalScore(){ }

    public static TopGoalScore getInstance(){
        if(instance == null){
            instance = new TopGoalScore();
        }
        return  instance;
    }

    public void notifyWhenPlayerGoal(Player player){
        setValue("player", player);
        notifyAllObservers();
    }

    public Player getTopGoalScore() {
        return topGoalScorer;
    }

    public void setTopGoalScore(Player topGoalScorer) {
        this.topGoalScorer = topGoalScorer;
    }
}
