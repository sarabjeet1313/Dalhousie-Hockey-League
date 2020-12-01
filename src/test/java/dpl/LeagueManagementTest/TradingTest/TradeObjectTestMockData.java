package dpl.LeagueManagementTest.TradingTest;

import dpl.LeagueManagement.GameplayConfiguration.Aging;
import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.GameplayConfiguration.Injury;
import dpl.LeagueManagement.GameplayConfiguration.Trading;
import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.Standings.StandingInfo;
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
import dpl.SystemConfig;
import dpl.LeagueManagementTest.GameplayConfigurationTest.GameplayConfigMockData;
import dpl.LeagueManagementTest.TeamManagementTest.CoachMockData;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueManagementTest.TeamManagementTest.ManagerMockData;
import dpl.LeagueManagement.Trading.ITradePersistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TradeObjectTestMockData implements ITradePersistence {

    private static TradeObjectTestMockData instance;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
    private Player player1 = teamManagement.PlayerWithParameters("Player One", "forward", true, 1, 1, 1, 1, 1, false, false, 0,false, 23, 3, 1999, Boolean.FALSE);
    private Player player7 = teamManagement.PlayerWithParameters("Player Seven", "forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 23, 3, 1999, Boolean.FALSE);
    private Player player2 = teamManagement.PlayerWithParameters("Player Two", "defense", false, 51, 12, 12, 13, 12, false, true, 0, false, 23, 3, 1999, Boolean.FALSE);
    private Player player3 = teamManagement.PlayerWithParameters("Player Three", "goalie", false, 10, 19, 18, 15, 14, false, false, 0, false, 19, 5, 2000, Boolean.FALSE);
    private Player player4 = teamManagement.PlayerWithParameters("Agent1", "forward", false, 1, 11, 14, 12, 13, false, false, 0, false, 19, 5, 2000, Boolean.FALSE);
    private Player player5 = teamManagement.PlayerWithParameters("Agent2", "defense", false, 1, 100, 1, 1, 1, false, false, 0, false, 19, 5, 2000, Boolean.FALSE);
    private Player player6 = teamManagement.PlayerWithParameters("Agent3", "defense", false, 1, 1, 1, 1, 1, false, false, 0, false, 19, 5, 2000, Boolean.FALSE);
    private Player player8 = teamManagement.PlayerWithParameters("Player Eight", "forward", false, 1, 20, 20, 20, 20, false, false, 0, false, 19, 5, 2000, Boolean.FALSE);
    List<Player> playerList = new ArrayList<>();
    List<Player> playerList2 = new ArrayList<>();
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
    private HashMap<String, Double> gmTable = new HashMap<>();
    Trading trading = new Trading(8, 0.05, 2, 0.05, gmTable);
    Manager manager1 = teamManagement.ManagerWithDbParameters("Karen Potam", "normal", managerMock);
    Manager manager2 = teamManagement.ManagerWithDbParameters("Joseph Squidly", "normal", managerMock);
    Manager manager3 = teamManagement.ManagerWithDbParameters("Tom Spaghetti", "normal", managerMock);
    Coach coach1 = teamManagement.CoachWithDbParameters("Coach One", 0.1, 0.2, 0.1, 0.1, coachMock);
    Coach coach2 = teamManagement.CoachWithDbParameters("Coach Two", 0.1, 0.2, 0.1, 0.1, coachMock);
    Coach coach3 = teamManagement.CoachWithDbParameters("Coach Three", 0.1, 0.2, 0.1, 0.1, coachMock);
    Coach headCoach = teamManagement.CoachWithDbParameters("Mary Smith", 0.2, 0.3, 0.1, 0.4, coachMock);

    public static TradeObjectTestMockData getInstance() {
        if(null == instance) {
           instance = new TradeObjectTestMockData();
        }
        return instance;
    }

    public League getLeagueData() {
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player7);
        List<Player> agentList = Arrays.asList(player4, player5, player6);
        freePlayerList.addAll(agentList);
        coachList.add(coach1);
        coachList.add(coach2);
        coachList.add(coach3);
        managerList.add(manager3);

        playerList2.add(player4);
        playerList2.add(player5);
        playerList2.add(player6);
        playerList2.add(player8);
        Team team1 = teamManagement.TeamWithParameters("Boston", manager1, coach1, playerList, Boolean.FALSE);
        Team team2 = teamManagement.TeamWithParameters("Halifax", manager2, coach2, playerList2, Boolean.FALSE);
        ArrayList<Team> teamList = new ArrayList<>();
        teamList.add(team1);
        teamList.add(team2);
        Division division = teamManagement.DivisionWithParameters("Atlantic", teamList);
        List<Division> divisionList = new ArrayList<>();
        divisionList.add(division);
        Conference conference = teamManagement.ConferenceWithParameters("Eastern Conference", divisionList);
        List<Conference> conferenceList = new ArrayList<>();
        conferenceList.add(conference);
        GameplayConfig config = new GameplayConfig(aging, injury, training, trading, configMock);
        League league = teamManagement.LeagueWithDbParameters("Dalhousie Hockey League", conferenceList, freePlayerList, coachList, managerList,
                config, leagueMock);
        return league;
    }

    public List<String> getAllTeamNames() {
        List<String> teamNames = new ArrayList<>();
        teamNames.add("Boston");
        teamNames.add("Halifax");
        return teamNames;
    }

    public League getLeagueDataAfterTrade() {
        playerList.clear();
        playerList.add(player4);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player8);
        List<Player> agentList = Arrays.asList(player4, player5, player6);
        freePlayerList.addAll(agentList);
        coachList.add(coach1);
        coachList.add(coach2);
        coachList.add(coach3);
        managerList.add(manager3);
        playerList2.clear();
        playerList2.add(player1);
        playerList2.add(player5);
        playerList2.add(player6);
        playerList2.add(player7);
        Team team1 = teamManagement.TeamWithParameters("Boston", manager1, coach1, playerList, Boolean.FALSE);
        Team team2 = teamManagement.TeamWithParameters("Halifax", manager2, coach2, playerList2, Boolean.FALSE);
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

    @Override
    public List<String> getEligibleTeamName(int lossPoints, League league, StandingInfo standingInfo) {
        List<String> eligibleTradeTeam = new ArrayList<>();
        eligibleTradeTeam.add("Boston");
        return eligibleTradeTeam;
    }

    @Override
    public boolean resetTradeLossPoint(String teamName, StandingInfo standingInfo) {
        return Boolean.FALSE;
    }
}
