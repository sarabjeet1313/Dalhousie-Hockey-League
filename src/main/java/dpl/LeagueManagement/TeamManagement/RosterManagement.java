package dpl.LeagueManagement.TeamManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dpl.SystemConfig;

public class RosterManagement implements IRosterManagement {

	ITeamManagementAbstractFactory teamManagementAbstractFactory = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	ITeamInfo teamInfo = teamManagementAbstractFactory.Team();
	IPlayerInfo playerInfo = teamManagementAbstractFactory.Player();

	public RosterManagement() {
		super();
	}

	@Override
	public boolean checkRoster(String teamName, League league) {
		int forward = 16;
		int defence = 10;
		int goalie = 4;
		List<Player> tempPlayerList = teamInfo.getPlayersByTeam(teamName, league);

		for (Player p : tempPlayerList) {
			if (p.getPosition().equals(RosterManagementConstants.FORWARD.toString())) {
				forward--;
			}
			if (p.getPosition().equals(RosterManagementConstants.DEFENSE.toString())) {
				defence--;
			}
			if (p.getPosition().equals(RosterManagementConstants.GOALIE.toString())) {
				goalie--;
			}
		}

		if (forward == 0 && defence == 0 && goalie == 0) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	@Override
	public boolean updateActiveStatus(String teamName, League league) {

		List<Player> playerList = teamInfo.getPlayersByTeam(teamName, league);
		List<Player> updatedPlayerList = new ArrayList<>();
		List<Player> goalieList = new ArrayList<>();
		List<Player> nonInjuredList = new ArrayList<>();
		List<Player> injuredList = new ArrayList<>();

		for (int pIndex = 0; pIndex < playerList.size(); pIndex++) {
			Player tempPlayer = playerList.get(pIndex);
			if (tempPlayer.getDaysInjured() <= 0) {
				if (tempPlayer.getPosition().equals(RosterManagementConstants.GOALIE.toString())) {
					goalieList.add(tempPlayer);
				} else {
					nonInjuredList.add(tempPlayer);
				}
			} else {
				if (tempPlayer.getPosition().equals(RosterManagementConstants.GOALIE.toString())) {
					goalieList.add(tempPlayer);
				} else {
					injuredList.add(tempPlayer);
				}
			}
		}
		goalieList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));
		nonInjuredList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));
		injuredList.sort(Comparator.comparingDouble(p -> p.getPlayerStrength(p)));

		Collections.reverse(goalieList);
		Collections.reverse(nonInjuredList);
		Collections.reverse(injuredList);

		for (int index = 0; index < goalieList.size(); index++) {
			if (index < 2) {
				goalieList.get(index).setIsActive(Boolean.TRUE);
			} else {
				goalieList.get(index).setIsActive(Boolean.FALSE);
			}
		}

		if (nonInjuredList.size() > 18) {
			for (int index = 0; index < nonInjuredList.size(); index++) {
				if (index < 18) {
					nonInjuredList.get(index).setIsActive(Boolean.TRUE);
				} else {
					nonInjuredList.get(index).setIsActive(Boolean.FALSE);
					for (int indexInjured = 0; indexInjured < injuredList.size(); indexInjured++) {
						injuredList.get(indexInjured).setIsActive(Boolean.FALSE);
					}
				}
			}
		} else {
			for (int index = 0; index < nonInjuredList.size(); index++) {
				nonInjuredList.get(index).setIsActive(Boolean.TRUE);
			}
			int tempCount = 18 - nonInjuredList.size();
			for (int indexCount = 0; indexCount < injuredList.size(); indexCount++) {
				if (indexCount < tempCount) {
					injuredList.get(indexCount).setIsActive(Boolean.TRUE);
				} else {
					injuredList.get(indexCount).setIsActive(Boolean.FALSE);
				}

			}
		}
		updatedPlayerList.addAll(goalieList);
		updatedPlayerList.addAll(nonInjuredList);
		updatedPlayerList.addAll(injuredList);

		teamInfo.setPlayersByTeam(teamName, updatedPlayerList, league);

		return true;
	}

	@Override
	public League updateLeagueActiveStatus(League league) {
		String currentTeamName;
		List<Conference> conferenceList = league.getConferenceList();
		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					currentTeamName = teamList.get(tIndex).getTeamName();
					updateActiveStatus(currentTeamName, league);
				}
			}
		}
		return league;
	}

	public Player getStrongestPlayer(List<Player> playerList, String playerType) {
		double maxStrength = 0;
		Player strongestPlayer = null;
		for (Player player : playerList) {
			if (player.getPosition().equals(playerType)) {
				if (maxStrength < playerInfo.getPlayerStrength(player)) {
					maxStrength = playerInfo.getPlayerStrength(player);
					strongestPlayer = player;
				}
			}
		}
		return strongestPlayer;
	}

	public Player getWeakestPlayer(List<Player> playerList, String playerType) {
		double minStrength = 999;
		Player weakestPlayer = null;
		for (Player player : playerList) {
			if (player.getPosition().equals(playerType)) {
				if (minStrength > playerInfo.getPlayerStrength(player)) {
					minStrength = playerInfo.getPlayerStrength(player);
					weakestPlayer = player;
				}
			}
		}
		return weakestPlayer;
	}

	@Override
	public League balanceOutRoster(League league) {
		String currentTeamName;
		List<Conference> conferenceList = league.getConferenceList();
		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					currentTeamName = teamList.get(tIndex).getTeamName();
					balanceOutSingleRoster(currentTeamName, league);
				}
			}
		}

		return league;
	}

	@Override
	public boolean balanceOutSingleRoster(String currentTeamName, League league) {
		List<Player> freeAgentList = league.getFreeAgents();
		boolean isValid = checkRoster(currentTeamName, league);
		boolean isBalanced = Boolean.FALSE;
		List<Player> tempPlayerList = teamInfo.getPlayersByTeam(currentTeamName,league);
		int forward = 16;
		int defence = 10;
		int goalie = 4;
		if(checkRoster(currentTeamName, league)==Boolean.FALSE){
			for (Player p: tempPlayerList){
				if (p.getPosition().equals(RosterManagementConstants.FORWARD.toString())){
					forward--;
				}
				if(p.getPosition().equals(RosterManagementConstants.DEFENSE.toString())){
					defence--;
				}
				if(p.getPosition().equals(RosterManagementConstants.GOALIE.toString())){
					goalie--;
				}
			}
			Player tempPlayer;
			if(forward < 0 ){
				while(forward<0){
					tempPlayer= getStrongestPlayer(freeAgentList,RosterManagementConstants.FORWARD.toString());
					tempPlayerList.add(tempPlayer);
					freeAgentList.remove(tempPlayer);
					forward++;
					if(forward == 0){
						break;
					}
				}
			}else{
				while(forward>0){
					tempPlayer = getWeakestPlayer(tempPlayerList, RosterManagementConstants.FORWARD.toString());
					tempPlayerList.remove(tempPlayer);
					freeAgentList.add(tempPlayer);
					forward--;
					if(forward == 0){
						break;
					}
				}
			}
			if(defence < 0 ){
				while(defence<0){
					tempPlayer= getStrongestPlayer(freeAgentList,RosterManagementConstants.DEFENSE.toString());
					tempPlayerList.add(tempPlayer);
					freeAgentList.remove(tempPlayer);
					defence++;
					if(defence == 0){
						break;
					}
				}
			}else{
				while(defence>0){
					tempPlayer = getWeakestPlayer(tempPlayerList, RosterManagementConstants.DEFENSE.toString());
					tempPlayerList.remove(tempPlayer);
					freeAgentList.add(tempPlayer);
					defence--;
					if(defence == 0){
						break;
					}
				}
			}
			if(goalie < 0 ){
				while(goalie<0){
					tempPlayer= getStrongestPlayer(freeAgentList,RosterManagementConstants.GOALIE.toString());
					tempPlayerList.add(tempPlayer);
					freeAgentList.remove(tempPlayer);
					goalie++;
					if(goalie == 0){
						break;
					}
				}
			}else{
				while(goalie>0){
					tempPlayer = getWeakestPlayer(tempPlayerList, RosterManagementConstants.GOALIE.toString());
					tempPlayerList.remove(tempPlayer);
					freeAgentList.add(tempPlayer);
					goalie--;
					if(goalie == 0){
						break;
					}
				}
			}
		   isBalanced = Boolean.TRUE;
		}
		teamInfo.setPlayersByTeam(currentTeamName,tempPlayerList, league);
		freeAgentList.removeAll(Collections.singleton(null));
		league.setFreeAgents(freeAgentList);
		return isBalanced;
	}

}
