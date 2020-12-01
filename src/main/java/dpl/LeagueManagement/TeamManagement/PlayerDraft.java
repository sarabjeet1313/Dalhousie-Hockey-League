package dpl.LeagueManagement.TeamManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import dpl.LeagueManagement.Trading.ITrade;
import dpl.LeagueManagement.Trading.ITradingAbstractFactory;
import dpl.SystemConfig;

public class PlayerDraft implements IPlayerDraft {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	private ITradingAbstractFactory tradingFactory = SystemConfig.getSingleInstance()
			.getTradingAbstractFactory();

	private static final Logger log = Logger.getLogger(PlayerDraft.class.getName());
	public final String GENERATE_DRAFT_PLAYER = "Generating playes for drafting";
	public final String START_DRAFTING = "Drafting Started Round : ";
	public final String NORMALIZE_DRAFT_PLAYERS = "Resolving the drafted players. Normalizing the Team players to 30";

	@Override
	public List<Team> generateDraftingTeams(List<String> teamList, League league) {
		List<Conference> conferenceList = league.getConferenceList();
		List<Division> divisionList = null;
		List<Team> teamObjList = null;
		List<Team> selectedTeamList = new ArrayList<>();

		for (int index = 0; index < conferenceList.size(); index++) {
			divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				teamObjList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamObjList.size(); tIndex++) {
					String teamName = teamObjList.get(tIndex).getTeamName();
					for (String tName : teamList) {
						if (teamName.equals(tName)) {
							selectedTeamList.add(teamObjList.get(tIndex));
						}
					}
				}
			}
		}
		return selectedTeamList;
	}

	@Override
	public List<Player> generateDraftingPlayers(int teamCount) {
		int numberOfPlayers = teamCount * 7;
		int forwordNumber = numberOfPlayers / 2;
		int defenceNumber = (int) ((numberOfPlayers) * 0.4);
		int goalieNumber = numberOfPlayers - forwordNumber - defenceNumber;
		List<Player> playerList = new ArrayList<>();
		String position = "";
		for (int index = 0; index < numberOfPlayers; index++) {
			if (forwordNumber > 0) {
				position = PlayerConstants.FORWARD.toString();
				forwordNumber = forwordNumber - 1;
			} else if (defenceNumber > 0) {
				position = PlayerConstants.DEFENCE.toString();
				defenceNumber = defenceNumber - 1;
			} else if (goalieNumber > 0) {
				position = PlayerConstants.GOALIE.toString();
				goalieNumber = goalieNumber - 1;
			}
			playerList.add(generatePlayerStat(position, index + 1));
		}
		log.log(Level.INFO, GENERATE_DRAFT_PLAYER);
		return playerList;
	}

	private Player generatePlayerStat(String playerType, int count) {
		int skating = 0;
		int shooting = 0;
		int checking = 0;
		int saving = 0;
		int minSkating = 0;
		int minShooting = 0;
		int minChecking = 0;
		int minSaving = 0;
		int maxSkating = 0;
		int maxShooting = 0;
		int maxChecking = 0;
		int maxSaving = 0;
		int day = 0;
		int month = 0;
		int year = 0;
		if (playerType.equals(PlayerConstants.GOALIE.toString())) {
			minSkating = 8;
			maxSkating = 15;
			minShooting = 1;
			maxShooting = 10;
			minChecking = 1;
			maxChecking = 12;
			minSaving = 12;
			maxSaving = 20;
		} else if (playerType.equals(PlayerConstants.FORWARD.toString())) {
			minSkating = 12;
			maxSkating = 20;
			minShooting = 12;
			maxShooting = 20;
			minChecking = 9;
			maxChecking = 18;
			minSaving = 1;
			maxSaving = 7;
		} else {
			minSkating = 10;
			maxSkating = 19;
			minShooting = 9;
			maxShooting = 18;
			minChecking = 12;
			maxChecking = 20;
			minSaving = 1;
			maxSaving = 12;
		}
		skating = new Random().nextInt((maxSkating - minSkating) + 1) + minSkating;
		shooting = new Random().nextInt((maxSkating - maxShooting) + 1) + minShooting;
		checking = new Random().nextInt((maxChecking - minChecking) + 1) + minChecking;
		saving = new Random().nextInt((maxSaving - minSaving) + 1) + minSaving;
		day = new Random().nextInt(29) + 1;
		month = new Random().nextInt(12) + 1;
		year = new Random().nextInt(2) + 1998;
		Player player = teamManagement.PlayerWithParameters("Player " + count, playerType, Boolean.FALSE, 2020 - year,
				skating, shooting, checking, saving, Boolean.FALSE, Boolean.FALSE, 0, Boolean.FALSE, day, month, year,
				Boolean.TRUE);
		return player;
	}

	@Override
	public List<Team> startRoundDraft(List<Team> teamList, List<Player> playerList, League league) {
		ITrade trade = tradingFactory.Trade();
		int pIndex = 0;
		for (int index = 0; index < 7; index++) {
			log.log(Level.INFO, START_DRAFTING + (index+1));
			for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
				if (playerList.size() > 0) {
					List<Player> tempList = teamList.get(tIndex).getPlayerList();
					tempList.add(playerList.get(pIndex));
					playerList.remove(pIndex);
					teamList.get(tIndex).setPlayerList(tempList);
				}
			}
			teamList = trade.startTradeDraftPick(league, teamList);
		}
		return teamList;
	}

	public League postDrafting(List<Team> teamList, League league) {
		List<Player> extraPlayerList = new ArrayList<>();
		List<Conference> conferenceList = league.getConferenceList();
		List<Division> divisionList = null;
		List<Team> teamObjList = null;
		if(teamList.size() == 0) {
			return league;
		}
		if(teamList.get(0).getPlayerList().size() < 30) {
			return league;
		}

		for (int index = 0; index < conferenceList.size(); index++) {
			divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				teamObjList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamObjList.size(); tIndex++) {
					Team tempTeam = teamObjList.get(tIndex);
					for (Team team : teamList) {
						if (tempTeam.getTeamName().equals(team.getTeamName())) {
							List<List<Player>> tempList = selectTopPlayer(tempTeam.getPlayerList());
							teamObjList.get(tIndex).setPlayerList(tempList.get(0));
							extraPlayerList.addAll(tempList.get(1));
						}
					}
				}
			}
		}
		league.getFreeAgents().addAll(extraPlayerList);
		log.log(Level.INFO, NORMALIZE_DRAFT_PLAYERS);
		return league;
	}

	private List<List<Player>> selectTopPlayer(List<Player> playerList) {
		List<List<Player>> returnList = new ArrayList<>();
		List<Player> tempList = new ArrayList<>();
		List<Player> tempList1 = new ArrayList<>();
		int forwordCount = 16;
		int defenceCount = 10;
		int goalieCount = 4;
		for (int index = 0; index < playerList.size(); index++) {
			Player player = playerList.get(index);
			if (player.isInjured() == Boolean.FALSE && forwordCount > 0 && defenceCount > 0 && goalieCount > 0) {
				if (player.getPosition().equals(PlayerConstants.FORWARD.toString())) {
					tempList.add(player);
					forwordCount = forwordCount - 1;
				} else if (player.getPosition().equals(PlayerConstants.DEFENCE.toString())) {
					tempList.add(player);
					defenceCount = defenceCount - 1;
				} else {
					tempList.add(player);
					goalieCount = goalieCount - 1;
				}
			}
		}
		playerList.removeAll(tempList);
		for (int index = 0; index < playerList.size(); index++) {
			Player player = playerList.get(index);
			if ((forwordCount + defenceCount + goalieCount) == 0) {
				break;
			}
			if (forwordCount > 0 && player.getPosition().equals(PlayerConstants.FORWARD.toString())) {
				tempList1.add(player);
				forwordCount = forwordCount - 1;
			} else if (defenceCount > 0 && player.getPosition().equals(PlayerConstants.DEFENCE.toString())) {
				tempList1.add(player);
				defenceCount = defenceCount - 1;
			} else if (goalieCount > 0 && player.getPosition().equals(PlayerConstants.GOALIE.toString())) {
				tempList1.add(player);
				goalieCount = goalieCount - 1;
			}
		}
		playerList.removeAll(tempList1);
		tempList.addAll(tempList1);
		returnList.add(tempList);
		returnList.add(playerList);
		return returnList;
	}
}
