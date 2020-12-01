package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerGoalScoreObserver implements IObserver {
	private List<Player> playerList;

	public PlayerGoalScoreObserver() {
		playerList = new ArrayList<>();
	}

	@Override
	public void update(Subject subject) {
		Player bestPlayer = null;
		int saves = 0;
		int goals = 0;
		playerList.add((Player) subject.getValue(TrophySystemConstants.PLAYER.toString()));

		for (Player player : playerList) {
			if (saves <= player.getSaves() || goals <= player.getGoals()) {
				saves = player.getSaves();
				goals = player.getGoals();
				bestPlayer = player;
			}
		}
		PlayerGoalScore.getInstance().setBestPlayer(bestPlayer);
	}
}
