package dal.asd.dpl.GameplayConfiguration;

import java.util.ArrayList;
import java.util.List;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Team;

public class Training {
	private int daysUntilStatIncreaseCheck;

	public Training(int daysUntilStatIncreaseCheck) {
		this.daysUntilStatIncreaseCheck = daysUntilStatIncreaseCheck;
	}

	public int getDaysUntilStatIncreaseCheck() {
		return daysUntilStatIncreaseCheck;
	}

	public void setDaysUntilStatIncreaseCheck(int daysUntilStatIncreaseCheck) {
		this.daysUntilStatIncreaseCheck = daysUntilStatIncreaseCheck;
	}

	public double generateRandomValue() {
		return Math.random();
	}

	public void updateStats(Player player, Coach headCoach) {
		double randomValue = generateRandomValue();
		if (randomValue < headCoach.getSkating()) {
			player.setSkating(player.getSkating() + 1);
		} else {
			// Call injury method
		}
		if (randomValue < headCoach.getShooting()) {
			player.setShooting(player.getShooting() + 1);
		} else {
			// Call injury method
		}
		if (randomValue < headCoach.getChecking()) {
			player.setChecking(player.getChecking() + 1);
		} else {
			// Call injury method
		}
		if (randomValue < headCoach.getSaving()) {
			player.setShooting(player.getShooting() + 1);
		} else {
			// Call injury method
		}
	}

	public League playerTraining(League league) {
		League leagueObject = league;
		Coach headCoach;
		List<Conference> conferenceList = league.getConferenceList();
		List<Team> teamList;
		List<Division> divisionList;
		List<Player> playerList = new ArrayList<Player>();
		for (int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
			divisionList = conferenceList.get(cIndex).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					headCoach = teamList.get(tIndex).getHeadCoach();
					playerList = teamList.get(tIndex).getPlayerList();
					for (int pIndex = 0; pIndex < playerList.size(); pIndex++) {
						updateStats(playerList.get(pIndex), headCoach);
					}
				}
			}
		}
		return leagueObject;
	}
}
