package dpl.LeagueManagement.TeamManagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dpl.SystemConfig;

public class AllStarGameManagement implements IAllStarGameManagement {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	static Map<Player, String> playersATeam = new HashMap<>();
	static Map<Player, String> playersBTeam = new HashMap<>();

	@Override
	public List<Team> performAllStarGame(League league) {

		List<Conference> conferenceList = league.getConferenceList();
		List<String> teamNameList = new ArrayList<String>();
		Map<String, List<Player>> forwardplayersList = getSortedPlayersByType(conferenceList,
				GeneralConstants.FORWARD.toString());
		Map<String, List<Player>> defenseplayersList = getSortedPlayersByType(conferenceList,
				GeneralConstants.DEFENSE.toString());
		Map<String, List<Player>> goalieplayersList = getSortedPlayersByType(conferenceList,
				GeneralConstants.GOALIE.toString());
		List<Team> teamsList = new ArrayList<Team>();
		Team teamA = teamManagement.Team();
		Team teamB = teamManagement.Team();
		teamA.setPlayerList(new ArrayList<>());
		teamB.setPlayerList(new ArrayList<>());
		int fIndex = 0;
		int dIndex = 0;
		int gIndex = 0;

		for (Map.Entry<String, List<Player>> entry : forwardplayersList.entrySet()) {
			if (fIndex <= 17) {
				Player firstPlayer = entry.getValue().get(0);
				if (fIndex % 2 == 0) {
					teamA.getPlayerList().add(firstPlayer);
					playersATeam.put(firstPlayer, entry.getKey());
				} else {
					teamB.getPlayerList().add(firstPlayer);
					playersBTeam.put(firstPlayer, entry.getKey());
				}
				entry.getValue().remove(0);
				teamNameList.add(entry.getKey());
			}
			fIndex++;
		}

		for (Map.Entry<String, List<Player>> entry : defenseplayersList.entrySet()) {
			if (dIndex < 2) {
				Player firstPlayer = entry.getValue().get(0);
				if (dIndex % 2 == 0) {
					teamA.getPlayerList().add(firstPlayer);
					playersATeam.put(firstPlayer, entry.getKey());
				} else {
					teamB.getPlayerList().add(firstPlayer);
					playersBTeam.put(firstPlayer, entry.getKey());
				}
				entry.getValue().remove(0);
				dIndex++;
				continue;
			}
			if (teamNameList.contains(entry.getKey())) {
				continue;
			} else {
				if (dIndex <= 17) {
					Player firstPlayer = entry.getValue().get(0);
					if (dIndex % 2 == 0) {
						teamA.getPlayerList().add(firstPlayer);
						playersATeam.put(firstPlayer, entry.getKey());
					} else {
						teamB.getPlayerList().add(firstPlayer);
						playersBTeam.put(firstPlayer, entry.getKey());
					}
					entry.getValue().remove(0);
				}
				dIndex++;
			}
		}

		for (Map.Entry<String, List<Player>> entry : goalieplayersList.entrySet()) {
			if (gIndex < 2) {
				Player firstPlayer = entry.getValue().get(0);
				if (gIndex % 2 == 0) {
					teamA.getPlayerList().add(firstPlayer);
					playersATeam.put(firstPlayer, entry.getKey());
				} else {
					teamB.getPlayerList().add(firstPlayer);
					playersBTeam.put(firstPlayer, entry.getKey());
				}
				entry.getValue().remove(0);
				gIndex++;
				continue;
			}
			if (teamNameList.contains(entry.getKey())) {
				continue;
			} else {
				if (gIndex <= 3) {
					Player firstPlayer = entry.getValue().get(0);
					if (gIndex % 2 == 0) {
						teamA.getPlayerList().add(firstPlayer);
						playersATeam.put(firstPlayer, entry.getKey());
					} else {
						teamB.getPlayerList().add(firstPlayer);
						playersBTeam.put(firstPlayer, entry.getKey());
					}
					entry.getValue().remove(0);
				}
				gIndex++;
			}
		}

		teamA.setTeamName(TeamManagementConstants.TEAM_A.toString());
		teamB.setTeamName(TeamManagementConstants.TEAM_B.toString());
		teamsList.add(teamA);
		teamsList.add(teamB);

		return teamsList;
	}
	
	@Override
	public Map<String, List<Player>> getSortedPlayersByType(List<Conference> conferenceList, String PlayerType) {
		Map<String, List<Player>> playersInTeamByType = new HashMap<>();

		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					List<Player> playerList = teamList.get(tIndex).getPlayerList();
					List<Player> tempList = new ArrayList<>();
					for (int pIndex = 0; pIndex < playerList.size(); pIndex++) {
						if (playerList.get(pIndex).getPosition().equalsIgnoreCase(PlayerType)) {
							tempList.add(playerList.get(pIndex));
							tempList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));
							playersInTeamByType.put(teamList.get(tIndex).getTeamName(), tempList);
						}
					}
				}
			}
		}

		return playersInTeamByType;
	}
	
	@Override
	public List<Map<Player, String>> getPlayersBytTeam() {
		List<Map<Player, String>> playersByTeam = new ArrayList<Map<Player,String>>();
		
		playersByTeam.add(playersATeam);
		playersByTeam.add(playersBTeam);
		
		return playersByTeam;
	}
}
