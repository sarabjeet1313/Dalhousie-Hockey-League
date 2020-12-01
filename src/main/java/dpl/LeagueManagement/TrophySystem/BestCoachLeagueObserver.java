package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BestCoachLeagueObserver implements IObserver {
	private Map<Coach, Integer> coach;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	public BestCoachLeagueObserver() {
		coach = new HashMap<>();
	}

	@Override
	public void update(Subject subject) {
		try {
			if (null == subject) {
				throw new NullPointerException();
			}
			Coach coach = (Coach) subject.getValue(TrophySystemConstants.COACH.toString());
			int statPlayer = (Integer) subject.getValue(TrophySystemConstants.STAT_PLAYER.toString());
			if (this.coach.containsKey(coach)) {
				this.coach.put(coach, this.coach.get(coach) + 1);
			} else {
				this.coach.put(coach, statPlayer);
			}
			Coach bestCoach = null;
			int statCount = 0;
			Set<Coach> keys = this.coach.keySet();
			for (Coach key : keys) {
				if (statCount <= this.coach.get(key)) {
					statCount = this.coach.get(key);
					bestCoach = key;
				}
			}
			BestCoachLeague.getInstance().setBestCoach(bestCoach);
			if (null == bestCoach) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(TrophySystemConstants.NULLPOINTER_MESSAGE.toString());
			output.sendOutput();
		}
	}
}
