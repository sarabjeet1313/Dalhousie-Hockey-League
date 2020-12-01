package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoalSaveObserver implements IObserver {
	private List<Player> playerList;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	public GoalSaveObserver() {
		playerList = new ArrayList<>();
	}

	@Override
	public void update(Subject subject) {
		try {
			if (null == subject) {
				throw new NullPointerException();
			}
			Player bestGoalSaver = null;
			int saves = 0;
			playerList.add((Player) subject.getValue(TrophySystemConstants.PLAYER.toString()));
			for (Player player : playerList) {
				if (saves <= player.getSaves()) {
					saves = player.getSaves();
					bestGoalSaver = player;
				}
			}
			GoalSave.getInstance().setBestGoalSaver(bestGoalSaver);
			if (null == bestGoalSaver) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(TrophySystemConstants.NULLPOINTER_MESSAGE.toString());
			output.sendOutput();
		}
	}
}
