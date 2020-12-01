package dpl.LeagueManagementTest.TeamManagementTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dpl.SystemConfig;
import dpl.LeagueManagementTest.GameplayConfigurationTest.GameplayConfigMockData;
import dpl.LeagueManagement.GameplayConfiguration.Aging;
import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigurationAbstractFactory;
import dpl.LeagueManagement.GameplayConfiguration.Injury;
import dpl.LeagueManagement.GameplayConfiguration.Trading;
import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.Team;

public class LeagueObjectTestData {

	HashMap<String, Double> gmTable = new HashMap<>();
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	private IGameplayConfigurationAbstractFactory gameConfig = SystemConfig.getSingleInstance()
			.getGameplayConfigurationAbstractFactory();
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
	List<Player> playerList = new ArrayList<>();
	List<Player> freePlayerList = new ArrayList<>();
	List<Coach> coachList = new ArrayList<>();
	List<Manager> managerList = new ArrayList<>();
	Aging aging = new Aging(35, 50, 0.02);
	Injury injury = new Injury(0.05, 1, 260);
	Training training = new Training(100, 100);
	private ILeaguePersistance leagueMock = new LeagueMockData();
	private ICoachPersistance coachMock = new CoachMockData();
	private IGameplayConfigPersistance configMock = new GameplayConfigMockData();
	private IManagerPersistance managerMock = new ManagerMockData();

	Trading trading = gameConfig.Trading(8, 0.05, 2, 0.05, gmTable);
	Manager manager1 = teamManagement.ManagerWithDbParameters("Karen Potam","normal", managerMock);
	Manager manager2 = teamManagement.ManagerWithDbParameters("Joseph Squidly","normal", managerMock);
	Manager manager3 = teamManagement.ManagerWithDbParameters("Tom Spaghetti","normal", managerMock);
	Coach coach1 = teamManagement.CoachWithDbParameters("Coach One", 0.1, 0.2, 0.1, 0.1, coachMock);
	Coach coach2 = teamManagement.CoachWithDbParameters("Coach Two", 0.1, 0.2, 0.1, 0.1, coachMock);
	Coach coach3 = teamManagement.CoachWithDbParameters("Coach Three", 0.1, 0.2, 0.1, 0.1, coachMock);
	Coach headCoach = teamManagement.CoachWithDbParameters("Mary Smith", 0.2, 0.3, 0.1, 0.4, coachMock);

	public League getLeagueData() {
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
		Team team1 = teamManagement.TeamWithParameters("Boston", manager1, coach1, playerList, Boolean.FALSE);
		Team team2 = teamManagement.TeamWithParameters("Halifax", manager2, coach2, playerList, Boolean.FALSE);
		ArrayList<Team> teamList = new ArrayList<Team>();
		teamList.add(team1);
		teamList.add(team2);
		Division division = teamManagement.DivisionWithParameters("Atlantic", teamList);
		List<Division> divisionList = new ArrayList<Division>();
		divisionList.add(division);
		Conference conference = teamManagement.ConferenceWithParameters("Eastern Conference", divisionList);
		List<Conference> conferenceList = new ArrayList<Conference>();
		conferenceList.add(conference);
		GameplayConfig config = new GameplayConfig(aging, injury, training, trading, configMock);
		League league = teamManagement.LeagueWithDbParameters("Dalhousie Hockey League", conferenceList, freePlayerList, coachList, managerList,
				config, leagueMock);
		return league;
	}
}
