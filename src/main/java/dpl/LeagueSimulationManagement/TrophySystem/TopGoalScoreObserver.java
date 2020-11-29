package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

import java.util.ArrayList;
import java.util.List;

public class TopGoalScoreObserver implements IObserver{
    private List<Player> playerList;

    public TopGoalScoreObserver(){
        playerList = new ArrayList<>();
    }

    @Override
    public void update(Subject subject) {
        Player bestPlayer = null;
        int goals = 0;
        playerList.add((Player) subject.getValue("player"));

        for(Player player: playerList){
            if(goals < player.getGoals()){
                goals = player.getGoals();
                bestPlayer = player;
            }
        }
        TopGoalScore.getInstance().setTopGoalScorer(bestPlayer);
    }
}
