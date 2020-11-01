package dal.asd.dpl.TeamManagementTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dal.asd.dpl.GameplayConfiguration.Aging;
import dal.asd.dpl.GameplayConfiguration.GameResolver;
import dal.asd.dpl.GameplayConfiguration.GameplayConfig;
import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.GameplayConfiguration.Injury;
import dal.asd.dpl.GameplayConfiguration.Trading;
import dal.asd.dpl.GameplayConfiguration.Training;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.ICoachPersistance;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Manager;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Team;

public class LeagueObjectTestData {

	private Player player1 = new Player("Player One", "forward", true, 1, 1, 1, 1, 1, false, false, 0);
	private Player player2 = new Player("Player Two", "defense", false, 51, 1, 1, 1, 1, false, true, 0);
	private Player player3 = new Player("Player Three", "goalie", false, 1, 1, 1, 1, 1, false, false, 0);
	private Player player4 = new Player("Agent1", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
	private Player player5 = new Player("Agent2", "defense", false, 1, 100, 1, 1, 1, false, false, 0);
	private Player player6 = new Player("Agent3", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent1 = new Player("Agent4", "forward", true, 1, 1, 1, 1, 1, false, false, 0);
	Player agent2 = new Player("Agent5", "goalie", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent3 = new Player("Agent6", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent4 = new Player("Agent7", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent5 = new Player("Agent8", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent6 = new Player("Agent9", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent7 = new Player("Agent10", "forward", true, 1, 1, 1, 1, 1, false, false, 0);
	Player agent8 = new Player("Agent11", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent9 = new Player("Agent12", "goalie", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent10 = new Player("Agent13", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent11 = new Player("Agent14", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent12 = new Player("Agent15", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent13 = new Player("Agent16", "forward", true, 1, 1, 1, 1, 1, false, false, 0);
	Player agent14 = new Player("Agent17", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent15 = new Player("Agent18", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent16 = new Player("Agent19", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent17 = new Player("Agent20", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent18 = new Player("Agent21", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent19 = new Player("Agent22", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent20 = new Player("Agent23", "goalie", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent21 = new Player("Agent24", "forward", true, 1, 1, 1, 1, 1, false, false, 0);
	Player agent22 = new Player("Agent25", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent23 = new Player("Agent26", "goalie", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent24 = new Player("Agent27", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent25 = new Player("Agent28", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent26 = new Player("Agent29", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
	Player agent27 = new Player("Agent30", "goalie", false, 1, 1, 1, 1, 1, false, false, 0);
	List<Player> playerList = new ArrayList<Player>();
	List<Player> freePlayerList = new ArrayList<Player>();
	List<Coach> coachList = new ArrayList<Coach>();
	List<Manager> managerList = new ArrayList<Manager>();
	Aging aging = new Aging(35, 50);
	GameResolver gameResolver = new GameResolver(0.1);
	Injury injury = new Injury(0.05, 1, 260);
	Training training = new Training(100, 100);
	private ILeaguePersistance leagueMock = new LeagueMockData();
	private ICoachPersistance coachMock = new CoachMockData();
	private IGameplayConfigPersistance configMock = new GamaplayConfigMockData();
	private IManagerPersistance managerMock = new ManagerMockData();
	Trading trading = new Trading(8, 0.05, 2, 0.05);
	Manager manager1 = new Manager("Karen Potam", managerMock);
	Manager manager2 = new Manager("Joseph Squidly", managerMock);
	Manager manager3 = new Manager("Tom Spaghetti", managerMock);
	Coach coach1 = new Coach("Coach One", 0.1, 0.2, 0.1, 0.1, coachMock);
	Coach coach2 = new Coach("Coach Two", 0.1, 0.2, 0.1, 0.1, coachMock);
	Coach coach3 = new Coach("Coach Three", 0.1, 0.2, 0.1, 0.1, coachMock);
	Coach headCoach = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4, coachMock);

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
		Team team1 = new Team("Boston", manager1, coach1, playerList, Boolean.FALSE);
		Team team2 = new Team("Halifax", manager2, coach2, playerList, Boolean.FALSE);
		ArrayList<Team> teamList = new ArrayList<Team>();
		teamList.add(team1);
		teamList.add(team2);
		Division division = new Division("Atlantic", teamList);
		List<Division> divisionList = new ArrayList<Division>();
		divisionList.add(division);
		Conference conference = new Conference("Eastern Conference", divisionList);
		List<Conference> conferenceList = new ArrayList<Conference>();
		conferenceList.add(conference);
		GameplayConfig config = new GameplayConfig(aging, gameResolver, injury, training, trading, configMock);
		League league = new League("Dalhousie Hockey League", conferenceList, freePlayerList, coachList, managerList,
				config, leagueMock);
		return league;
	}
}
