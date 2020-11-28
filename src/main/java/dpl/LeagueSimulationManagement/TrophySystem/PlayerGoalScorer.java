package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

public class PlayerGoalScorer extends Subject{
    private static PlayerGoalScorer instance;
    private Player bestPlayer;

    private PlayerGoalScorer(){ }

    public static PlayerGoalScorer getInstance(){
        if(instance == null){
            instance = new PlayerGoalScorer();
        }
        return  instance;
    }

    public void notifyWhenPlayerGoal(Player player){
        setValue("player", player);
        notifyAllObservers();
    }

    public Player getBestPlayer() {
        return bestPlayer;
    }

    public void setBestPlayer(Player bestPlayer) {
        this.bestPlayer = bestPlayer;
    }
}
