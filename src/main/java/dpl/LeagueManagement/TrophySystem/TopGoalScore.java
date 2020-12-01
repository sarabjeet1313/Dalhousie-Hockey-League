package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Player;

public class TopGoalScore extends Subject {
	private static TopGoalScore instance;
	private Player topGoalScorer;

	public TopGoalScore() {
	}

	public static TopGoalScore getInstance() {
		if (instance == null) {
			instance = new TopGoalScore();
		}
		return instance;
	}

	public void notifyPlayerGoal(Player player) {
		setValue(TrophySystemConstants.PLAYER.toString(), player);
		notifyAllObservers();
	}

	public Player getTopGoalScore() {
		return topGoalScorer;
	}

	public void setTopGoalScore(Player topGoalScorer) {
		this.topGoalScorer = topGoalScorer;
	}
}
