package dpl.LeagueManagement.TrophySystem;

import dpl.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantAward extends Subject {
	private static ParticipantAward instance;
	private String teamWithLowestPoints;
	private static final Logger log = Logger.getLogger(RetirementManagement.class.getName());
	private IUserOutput output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();

	private ParticipantAward() {
	}

	public static ParticipantAward getInstance() {
		if (instance == null) {
			instance = new ParticipantAward();
		}
		return instance;
	}

	public void notifyParticipatedTeam(Team team) {
		try {
			if (null == team) {
				throw new NullPointerException();
			}
			setValue(TrophySystemConstants.TEAM.toString(), team);
			notifyAllObservers();
		} catch (NullPointerException e) {
			log.log(Level.SEVERE, TrophySystemConstants.EXCEPTION_MESSAGE.toString());
			output.setOutput(TrophySystemConstants.NULLPOINTER_MESSAGE.toString());
			output.sendOutput();
		}
	}

	public String getTeamWithLowestPoints() {
		return teamWithLowestPoints;
	}

	public void setTeamWithLowestPoints(String teamWithLowestPoints) {
		this.teamWithLowestPoints = teamWithLowestPoints;
	}
}
