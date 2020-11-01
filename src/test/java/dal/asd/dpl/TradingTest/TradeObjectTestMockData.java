package dal.asd.dpl.TradingTest;

import dal.asd.dpl.GameplayConfiguration.*;
import dal.asd.dpl.TeamManagement.*;
import dal.asd.dpl.TeamManagementTest.CoachMockData;
import dal.asd.dpl.TeamManagementTest.GamaplayConfigMockData;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.TeamManagementTest.ManagerMockData;
import dal.asd.dpl.Trading.ITradeDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TradeObjectTestMockData implements ITradeDB {


    private Player player1 = new Player("Player One", "forward", true, 1, 1, 1, 1, 1, false, false, 0);
    private Player player7 = new Player("Player Seven", "forward", false, 1, 1, 1, 1, 1, false, false, 0);
    private Player player2 = new Player("Player Two", "defense", false, 51, 12, 12, 13, 12, false, true, 0);
    private Player player3 = new Player("Player Three", "goalie", false, 10, 19, 18, 15, 14, false, false, 0);
    private Player player4 = new Player("Agent1", "forward", false, 1, 11, 14, 12, 13, false, false, 0);
    private Player player5 = new Player("Agent2", "defense", false, 1, 100, 1, 1, 1, false, false, 0);
    private Player player6 = new Player("Agent3", "defense", false, 1, 1, 1, 1, 1, false, false, 0);
    private Player player8 = new Player("Player Eight", "forward", false, 1, 20, 20, 20, 20, false, false, 0);
    List<Player> playerList = new ArrayList<Player>();
    List<Player> playerList2 = new ArrayList<Player>();
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
        Team team1 = new Team("Boston", manager1, coach1, playerList, Boolean.FALSE);
        Team team2 = new Team("Halifax", manager2, coach2, playerList2, Boolean.FALSE);
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
        Team team1 = new Team("Boston", manager1, coach1, playerList, Boolean.FALSE);
        Team team2 = new Team("Halifax", manager2, coach2, playerList2, Boolean.FALSE);
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

    @Override
    public List<String> getEligibleTeamName(int lossPoints) {
        List<String> eligibleTradeTeam = new ArrayList<>();
        eligibleTradeTeam.add("Boston");
        return eligibleTradeTeam;
    }

    @Override
    public int getLossPoint() {
        return 2;
    }

    @Override
    public int getMaxPlayersPerTrade() {
        return 2;
    }

    @Override
    public double getRandomTradeOfferChance() {
        return 1;
    }

    @Override
    public double getRandomTradeAcceptChance() {
        return 0;
    }

    @Override
    public String getUserteamName() {
        return "Halifax";
    }


}
