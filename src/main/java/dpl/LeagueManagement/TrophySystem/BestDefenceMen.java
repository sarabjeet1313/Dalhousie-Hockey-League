package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BestDefenceMen extends Subject {
	private static BestDefenceMen instance;
	private Player bestDefenceMen;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	BestDefenceMen() {
	}

	public static BestDefenceMen getInstance() {
		if (instance == null) {
			instance = new BestDefenceMen();
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

	public Player getBestDefenceMen() {
		return bestDefenceMen;
	}

	public void setBestDefenceMen(Player bestDefenceMen) {
		this.bestDefenceMen = bestDefenceMen;
	}
}
