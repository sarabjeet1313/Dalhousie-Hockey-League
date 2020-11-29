package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

import java.util.ArrayList;
import java.util.List;

public class GoalSaveObserver implements IObserver{
    private List<Player> playerList;

    public GoalSaveObserver(){
        playerList = new ArrayList<>();
    }

    @Override
    public void update(Subject subject) {
        Player bestGoalSaver = null;
        int saves = 0;
        playerList.add((Player) subject.getValue("player"));
        for(Player player: playerList){
            if(saves < player.getSaves()){
                saves = player.getSaves();
                bestGoalSaver = player;
            }
        }
        GoalSave.getInstance().setBestGoalSaver(bestGoalSaver);
    }
}
