package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;

import dal.asd.dpl.util.ConstantsUtil;

public class Team implements ITeamInfo {

	private String teamName;
	private Manager generalManager;
	private Coach headCoach;
	private List<Player> playerList;
	private boolean isNewTeam;

	public Team() {
		super();
	}

	public Team(String teamName, Manager generalManager, Coach headCoach, List<Player> playerList, boolean isNewTeam) {
		this.teamName = teamName;
		this.generalManager = generalManager;
		this.headCoach = headCoach;
		this.playerList = playerList;
		this.isNewTeam = isNewTeam;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Manager getGeneralManager() {
		return generalManager;
	}

	public void setGeneralManager(Manager generalManager) {
		this.generalManager = generalManager;
	}

	public Coach getHeadCoach() {
		return headCoach;
	}

	public void setHeadCoach(Coach headCoach) {
		this.headCoach = headCoach;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	public boolean isNewTeam() {
		return isNewTeam;
	}

	public void setNewTeam(boolean isNewTeam) {
		this.isNewTeam = isNewTeam;
	}

	public boolean isValidTeamName(String conferenceName, String divisionName, String teamName, League league) {
		List<Conference> conferenceList = league.getConferenceList();
		boolean isValid = Boolean.FALSE;
		for (int index = 0; index < conferenceList.size(); index++) {
			if (conferenceList.get(index).getConferenceName().equals(conferenceName)) {
				List<Division> divisionList = conferenceList.get(index).getDivisionList();

				for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
					if (divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
						List<Team> teamList = divisionList.get(dIndex).getTeamList();

						for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
							if (teamList.get(dIndex).getTeamName().equals(teamName)) {
								isValid = Boolean.TRUE;
								break;
							}
						}
					}
				}
			}
		}
		return isValid;
	}

	public List<List<Player>> getAvailablePlayersList(League league) {
		List<List<Player>> list = new ArrayList<List<Player>>();
		List<Player> playerList = league.getFreeAgents();
		List<Player> golieList = new ArrayList<Player>();
		List<Player> forwordList = new ArrayList<Player>();
		List<Player> defenceList = new ArrayList<Player>();
		for (int index = 0; index < playerList.size(); index++) {
			if (playerList.get(index).getPosition().equals(ConstantsUtil.GOALIE.toString())) {
				golieList.add(playerList.get(index));
			} else if (playerList.get(index).getPosition().equals(ConstantsUtil.FORWARD.toString())) {
				forwordList.add(playerList.get(index));
			} else {
				defenceList.add(playerList.get(index));
			}
		}
		list.add(golieList);
		list.add(forwordList);
		list.add(defenceList);
		return list;
	}

	@Override
	public List<Player> getPlayersByTeam(String teamName, League league) {
		List<Conference> conferenceList = league.getConferenceList();
		List<Player> playersByTeam = new ArrayList<Player>();
		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					if (teamList.get(dIndex).getTeamName().equals(teamName)) {
						playersByTeam = teamList.get(tIndex).getPlayerList();
						break;
					}
				}
			}
		}
		return playersByTeam;
	}

	@Override
	public double getTeamStrength(String teamName, League league) {
		List<Player> players = this.getPlayersByTeam(teamName, league);
		double teamStrength = 0.0;

		for (Player player : players) {
			teamStrength = teamStrength + player.getPlayerStrength(player);
		}

		return teamStrength;
	}

	@Override
	public boolean shouldReverseResult(double randomChance) {
		double result = Math.random();

		if (result < randomChance) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
