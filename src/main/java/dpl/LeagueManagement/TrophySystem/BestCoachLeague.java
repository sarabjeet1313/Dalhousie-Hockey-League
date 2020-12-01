package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BestCoachLeague extends Subject {
	private Coach bestCoach;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
	private static BestCoachLeague instance;

	private BestCoachLeague() {
	}

	public static BestCoachLeague getInstance() {
		if (instance == null) {
			instance = new BestCoachLeague();
		}
		return instance;
	}

	public void notifyCoachTraining(Coach coach, int statPlayer) {
		try {
			if (null == coach) {
				throw new NullPointerException();
			}
			setValue(TrophySystemConstants.COACH.toString(), coach);
			setValue(TrophySystemConstants.STAT_PLAYER.toString(), statPlayer);
			notifyAllObservers();
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(TrophySystemConstants.NULLPOINTER_MESSAGE.toString());
			output.sendOutput();
		}
	}

	public void setBestCoach(Coach coach) {
		this.bestCoach = coach;
	}

	public Coach getBestCoach() {
		return this.bestCoach;
	}
}
