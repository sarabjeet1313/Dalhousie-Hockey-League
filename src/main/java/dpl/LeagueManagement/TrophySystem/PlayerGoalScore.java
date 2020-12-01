package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerGoalScore extends Subject {
	private static PlayerGoalScore instance;
	private Player bestPlayer;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	private PlayerGoalScore() {
	}

	public static PlayerGoalScore getInstance() {
		if (instance == null) {
			instance = new PlayerGoalScore();
		}
		return instance;
	}

	public void notifyWhenPlayerGoal(Player player) {
		try {
			if (null == player) {
				throw new NullPointerException();
			}
			setValue(TrophySystemConstants.PLAYER.toString(), player);
			notifyAllObservers();
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(TrophySystemConstants.NULLPOINTER_MESSAGE.toString());
			output.sendOutput();
		}
	}

	public Player getBestPlayer() {
		return bestPlayer;
	}

	public void setBestPlayer(Player bestPlayer) {
		this.bestPlayer = bestPlayer;
	}
}
