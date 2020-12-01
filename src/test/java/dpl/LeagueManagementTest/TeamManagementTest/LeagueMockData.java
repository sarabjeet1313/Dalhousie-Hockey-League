package dpl.LeagueManagementTest.TeamManagementTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import dpl.SystemConfig;
import dpl.LeagueManagement.GameplayConfiguration.Aging;
import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueManagement.GameplayConfiguration.Injury;
import dpl.LeagueManagement.GameplayConfiguration.Trading;
import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.Team;

public class LeagueMockData implements ILeaguePersistance {

	private static LeagueMockData instance;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	private Player player1 = teamManagement.PlayerWithParameters("Player One", "forward", true, 49, 1, 1, 1, 1, false, false, 0,false, 20, 5, 2000, Boolean.FALSE);
	private Player player2 = teamManagement.PlayerWithParameters("Player Two", "defense", false, 51, 1, 1, 1, 1, false, true, 0, false, 20, 5, 2000, Boolean.FALSE);
	private Player player3 = teamManagement.PlayerWithParameters("Player Three", "goalie", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	private Player player4 = teamManagement.PlayerWithParameters("Agent1", "forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	private Player player5 = teamManagement.PlayerWithParameters("Agent2", "defense", false, 51, 1, 1, 1, 1, false, true, 0,false, 20, 5, 2000, Boolean.FALSE);
	private Player player6 = teamManagement.PlayerWithParameters("Agent3", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent1 = teamManagement.PlayerWithParameters("Agent4", "forward", true, 1, 1, 1, 1, 1, false, false, 0, false, 25, 7, 2001, Boolean.FALSE);
	Player agent2 = teamManagement.PlayerWithParameters("Agent5", "goalie", false, 50, 1, 1, 1, 1, false, false, 0, false, 25, 7, 2001, Boolean.FALSE);
	Player agent3 = teamManagement.PlayerWithParameters("Agent6", "forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 25, 7, 2001, Boolean.FALSE);
	Player agent4 = teamManagement.PlayerWithParameters("Agent7", "forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 25, 7, 2001, Boolean.FALSE);
	Player agent5 = teamManagement.PlayerWithParameters("Agent8", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 25, 7, 2001, Boolean.FALSE);
	Player agent6 = teamManagement.PlayerWithParameters("Agent9", "forward", false, 48, 1, 1, 1, 1, false, false, 0, false, 25, 7, 2001, Boolean.FALSE);
	Player agent7 = teamManagement.PlayerWithParameters("Agent10", "forward", true, 51, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent8 = teamManagement.PlayerWithParameters("Agent11", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent9 = teamManagement.PlayerWithParameters("Agent12", "goalie", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent10 = teamManagement.PlayerWithParameters("Agent13", "forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent11 = teamManagement.PlayerWithParameters("Agent14", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent12 = teamManagement.PlayerWithParameters("Agent15", "defense", false, 49, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent13 = teamManagement.PlayerWithParameters("Agent16", "forward", true, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent14 = teamManagement.PlayerWithParameters("Agent17", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent15 = teamManagement.PlayerWithParameters("Agent18", "forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent16 = teamManagement.PlayerWithParameters("Agent19", "forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent17 = teamManagement.PlayerWithParameters("Agent20", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent18 = teamManagement.PlayerWithParameters("Agent21", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent19 = teamManagement.PlayerWithParameters("Agent22", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent20 = teamManagement.PlayerWithParameters("Agent23", "goalie", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent21 = teamManagement.PlayerWithParameters("Agent24", "forward", true, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent22 = teamManagement.PlayerWithParameters("Agent25", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent23 = teamManagement.PlayerWithParameters("Agent26", "goalie", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent24 = teamManagement.PlayerWithParameters("Agent27", "forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent25 = teamManagement.PlayerWithParameters("Agent28", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent26 = teamManagement.PlayerWithParameters("Agent29", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Player agent27 = teamManagement.PlayerWithParameters("Agent30", "goalie", false, 1, 1, 1, 1, 1, false, false, 0, false, 20, 5, 2000, Boolean.FALSE);
	Coach coach1 = teamManagement.CoachWithParameters("Coach One", 0.1, 0.2, 0.1, 0.1);
	Coach coach2 = teamManagement.CoachWithParameters("Coach Two", 0.1, 0.2, 0.1, 0.1);
	Coach coach3 = teamManagement.CoachWithParameters("Coach Three", 0.1, 0.2, 0.1, 0.1);
	Coach headCoach = teamManagement.CoachWithParameters("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	Manager manager1 = teamManagement.ManagerWithParameters("Karen Potam", "normal");
	Manager manager2 = teamManagement.ManagerWithParameters("Joseph Squidly", "normal");
	Manager manager3 = teamManagement.ManagerWithParameters("Tom Spaghetti", "normal");
	List<Player> playerList = new ArrayList<>();
	List<Player> freePlayerList = new ArrayList<>();
	List<Coach> coachList = new ArrayList<>();
	List<Manager> managerList = new ArrayList<>();
	Aging aging = new Aging(35, 50, 0.02);
	Injury injury = new Injury(0.05, 1, 260);
	Training training = new Training(100, 100);
	HashMap<String, Double> gmTable = new HashMap<>();
	Trading trading = new Trading(8, 0.05, 2, 0.05, gmTable);
	League league = getTestData();
	IRetirementManagement retireManager = teamManagement.RetirementManagement();

	public static LeagueMockData getInstance() {
		if (instance == null) {
			instance = new LeagueMockData();
		}
		return instance;
	}

	public League getTestData() {
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		List<Player> agentList = Arrays.asList(player4, player5, player6, agent1, agent2, agent3, agent4, agent5,
				agent6, agent7, agent8, agent9, agent10, agent11, agent12, agent13, agent14, agent15, agent16, agent17,
				agent18, agent19, agent20, agent21, agent22, agent23, agent24, agent25, agent26, agent27);
		freePlayerList.addAll(agentList);
		coachList.add(coach1);
		coachList.add(coach2);
		coachList.add(coach3);
		managerList.add(manager3);
		Team team1 = teamManagement.TeamWithParameters("Boston", manager1, headCoach, playerList, Boolean.FALSE);
		Team team2 = teamManagement.TeamWithParameters("Halifax", manager2, headCoach, playerList, Boolean.FALSE);
		Team team3 = teamManagement.TeamWithParameters("Toronto", manager2, headCoach, playerList, Boolean.FALSE);
		ArrayList<Team> teamList = new ArrayList<Team>();
		teamList.add(team1);
		teamList.add(team2);
		teamList.add(team3);
		Division division = teamManagement.DivisionWithParameters("Atlantic", teamList);
		ArrayList<Division> divisionList = new ArrayList<Division>();
		divisionList.add(division);
		Conference conference1 = teamManagement.ConferenceWithParameters("Eastern Conference", divisionList);
		Conference conference2 = teamManagement.ConferenceWithParameters("Western Conference", divisionList);
		ArrayList<Conference> conferenceList = new ArrayList<Conference>();
		conferenceList.add(conference1);
		conferenceList.add(conference2);
		GameplayConfig config = new GameplayConfig(aging, injury, training, trading);
		League league = teamManagement.LeagueWithParameters("Dalhousie Hockey League", conferenceList, freePlayerList, coachList, managerList,
				config);
		return league;
	}

	@Override
	public League loadLeagueData(String teamName) {
		League league = getTestData();
		return league;
	}

	@Override
	public int checkLeagueName(League league) {
		League tempLeague = getTestData();
		int rowCount = 0;
		if (league.getLeagueName().equals(tempLeague.getLeagueName())) {
			rowCount = 1;
		}
		return rowCount;
	}

	@Override
	public boolean persisitLeagueData(League league, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, Player player) {
		if (teamName.equals("Empty")) {
			List<Player> playerList = new ArrayList<Player>();
			playerList.add(player1);
		}

		return true;
	}

	public boolean shouldPlayerRetire(League league, Player player) {
		int maximumAge = league.getGameConfig().getAging().getMaximumAge();
		int likelihoodOfRetirement = retireManager.getLikelihoodOfRetirement(league, player);
		Random rand = new Random();

		if (rand.nextInt(likelihoodOfRetirement) - 1 == 0 || player.getAge() > maximumAge) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public League replaceRetiredPlayers(League league) {
		List<Conference> conferenceList = league.getConferenceList();
		List<Player> freeAgentsList = league.getFreeAgents();
		List<Player> tempList = new ArrayList<Player>();

		for (Player freeplayer : freeAgentsList) {

			if (shouldPlayerRetire(league, freeplayer)) {
				freeplayer.setRetireStatus(true);
				tempList.add(freeplayer);
			}
		}

		freeAgentsList.removeAll(tempList);

		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					List<Player> playersByTeam = teamList.get(tIndex).getPlayerList();
					for (int pIndex = 0; pIndex < playersByTeam.size(); pIndex++) {
						if (playersByTeam.get(pIndex).isRetireStatus()) {
							int selectedIndex = 0;
							double max = 0;

							if (freeAgentsList.size() > 0) {

								for (int findex = 0; findex < freeAgentsList.size(); findex++) {
									Player freeAgent = freeAgentsList.get(findex);
									if (freeAgent.getPosition().equals(playersByTeam.get(pIndex).getPosition())) {
										if (max < freeAgent.getPlayerStrength(freeAgent)) {
											max = freeAgent.getPlayerStrength(freeAgent);
											selectedIndex = findex;
										}
									}
								}

								Player returnedPlayer = freeAgentsList.get(selectedIndex);

								freeAgentsList.remove(returnedPlayer);
								playersByTeam.remove(playersByTeam.get(pIndex));
								playersByTeam.add(returnedPlayer);

								league.setFreeAgents(freeAgentsList);
								league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList()
										.get(tIndex).setPlayerList(playersByTeam);
							}
						}
					}
				}
			}
		}
		return league;
	}

	public League increaseAge(int days, League league) {
		League tempLeague = null;
		List<Conference> conferenceList = league.getConferenceList();
		List<Player> freeAgentsList = league.getFreeAgents();

		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					List<Player> playersByTeam = teamList.get(tIndex).getPlayerList();
					for (Player player : playersByTeam) {
						int years = player.getAge();
						player.setAge(years + 1);

						if (shouldPlayerRetire(league, player)) {
							player.setRetireStatus(true);
						}
					}
					league.getConferenceList().get(index).getDivisionList().get(dIndex).getTeamList().get(tIndex)
							.setPlayerList(playersByTeam);
				}
			}
		}

		for (Player freeplayer : freeAgentsList) {
			int years = freeplayer.getAge();
			freeplayer.setAge(years + 1);

			if (shouldPlayerRetire(league, freeplayer)) {
				freeplayer.setRetireStatus(true);
			}
		}

		league.setFreeAgents(freeAgentsList);
		tempLeague = replaceRetiredPlayers(league);
		return tempLeague;
	}

	@Override
	public boolean updateLeagueData(League league, String teamName, Player player) {
		boolean isUpdated = Boolean.FALSE;
		League tempLeague = getTestData();
		if (league.getLeagueName().equals(tempLeague.getLeagueName())) {
			isUpdated = Boolean.TRUE;
		}
		return isUpdated;
	}
}
