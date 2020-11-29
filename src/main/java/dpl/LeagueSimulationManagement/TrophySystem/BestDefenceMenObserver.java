package dpl.LeagueSimulationManagement.TrophySystem;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;

import java.util.ArrayList;
import java.util.List;

public class BestDefenceMenObserver implements IObserver {
    private List<Player> playerList;

    public BestDefenceMenObserver() {
        playerList = new ArrayList<>();
    }

    @Override
    public void update(Subject subject) {
        Player bestPlayer = null;
        int penalties = 0;
        playerList.add((Player) subject.getValue(TrophySystemConstants.PLAYER.toString()));

        for (Player player : playerList) {
            if (penalties < player.getPenalties()) {
                penalties = player.getPenalties();
                bestPlayer = player;
            }
        }
        BestDefenceMen.getInstance().setBestDefenceMen(bestPlayer);
    }
}
