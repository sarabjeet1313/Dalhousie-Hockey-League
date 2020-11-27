package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

import java.util.ArrayList;
import java.util.List;

public class TopGoalScorerObserver implements IObserver{
    private List<Player> playerList;

    public TopGoalScorerObserver(){
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
        TopGoalScorer.getInstance().setTopGoalScorer(bestPlayer);
    }
}
