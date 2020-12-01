package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantAwardObserver implements IObserver {
	private Map<String, Integer> teamPoints;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	public ParticipantAwardObserver() {
		teamPoints = new HashMap<>();
	}

	@Override
	public void update(Subject subject) {
		try {
			if (null == subject) {
				throw new NullPointerException();
			}
			String teamWithLowPoints = null;
			int points = 0;
			Team team = (Team) subject.getValue(TrophySystemConstants.TEAM.toString());
			if (teamPoints.containsKey(team.getTeamName())) {
				teamPoints.put(team.getTeamName(), teamPoints.get(team.getTeamName()) + 1);
			} else {
				teamPoints.put(team.getTeamName(), 1);
			}

			if (teamPoints.size() > 0) {
				teamWithLowPoints = (String) teamPoints.keySet().toArray()[0];
				points = teamPoints.get(teamWithLowPoints);
			}
			if (teamPoints.size() > 1) {
				for (String teamName : teamPoints.keySet()) {
					if (points > teamPoints.get(teamName)) {
						teamWithLowPoints = teamName;
					}
					points = teamPoints.get(teamName);
				}
			}
			ParticipantAward.getInstance().setTeamWithLowestPoints(teamWithLowPoints);
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(TrophySystemConstants.NULLPOINTER_MESSAGE.toString());
			output.sendOutput();
		}
	}
}
