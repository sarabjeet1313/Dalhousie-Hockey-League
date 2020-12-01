package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GoalSave extends Subject {
	private static GoalSave instance;
	private Player bestGoalSaver;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	public GoalSave() {
	}

	public static GoalSave getInstance() {
		if (instance == null) {
			instance = new GoalSave();
		}
		return instance;
	}

	public void notifyPlayerSaveGoal(Player player) {
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

	public Player getBestGoalSaver() {
		return bestGoalSaver;
	}

	public void setBestGoalSaver(Player bestGoalSaver) {
		this.bestGoalSaver = bestGoalSaver;
	}
}
