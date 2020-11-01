package dal.asd.dpl.GameplayConfiguration;

import java.util.ArrayList;
import java.util.List;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.IInjuryManagement;
import dal.asd.dpl.TeamManagement.InjuryManagement;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Team;

public class Training {
	private int daysUntilStatIncreaseCheck;
	private int trackDays;

	public Training(int daysUntilStatIncreaseCheck, int trackDays) {
		this.daysUntilStatIncreaseCheck = daysUntilStatIncreaseCheck;
		this.trackDays = trackDays;
	}
	
	public Training() {
		
	}

	public int getDaysUntilStatIncreaseCheck() {
		return daysUntilStatIncreaseCheck;
	}

	public void setDaysUntilStatIncreaseCheck(int daysUntilStatIncreaseCheck) {
		this.daysUntilStatIncreaseCheck = daysUntilStatIncreaseCheck;
	}

	public int getTrackDays() {
		return trackDays;
	}

	public void setTrackDays(int trackDays) {
		this.trackDays = trackDays;
	}

	public double generateRandomValue() {
		return Math.random();
	}

	public void updateStats(Player player, Coach headCoach, League league) {
		double randomValue = generateRandomValue();
		IInjuryManagement injury = new InjuryManagement();  
		if (randomValue < headCoach.getSkating()) {
			player.setSkating(player.getSkating() + 1);
		} else {
			player = injury.getPlayerInjuryDays(player, league);
		}
		if (randomValue < headCoach.getShooting() && player.isInjured() == Boolean.FALSE) {
			player.setShooting(player.getShooting() + 1);
		} else {
			player = injury.getPlayerInjuryDays(player, league);
		}
		if (randomValue < headCoach.getChecking() && player.isInjured() == Boolean.FALSE) {
			player.setChecking(player.getChecking() + 1);
		} else {
			player = injury.getPlayerInjuryDays(player, league);
		}
		if (randomValue < headCoach.getSaving() && player.isInjured() == Boolean.FALSE) {
			player.setShooting(player.getShooting() + 1);
		} else {
			player = injury.getPlayerInjuryDays(player, league);
		}
	}

	private League playerTraining(League league) {
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
						updateStats(playerList.get(pIndex), headCoach, league);
					}
				}
			}
		}
		return leagueObject;
	}

	public League trackDaysForTraining(League league) {
		int days = league.getGameConfig().getTraining().getTrackDays()-1;
		if(days == 0) {
			league.getGameConfig().getTraining().setTrackDays(league.getGameConfig().getTraining().getDaysUntilStatIncreaseCheck());
			league = playerTraining(league);
		}
		league.getGameConfig().getTraining().setTrackDays(days);
		return league;
	}
}
