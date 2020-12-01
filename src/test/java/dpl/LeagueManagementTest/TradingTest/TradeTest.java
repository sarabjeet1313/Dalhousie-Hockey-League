package dpl.LeagueManagementTest.TradingTest;

import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.IPlayerInfo;
import dpl.LeagueManagement.TeamManagement.ITeamInfo;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.SystemConfig;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueManagement.Trading.Trade;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TradeTest {

    private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
    private static final Logger log = Logger.getLogger(TradeTest.class.getName());

    League leagueBefore = new TradeObjectTestMockData().getLeagueData();
    League leagueAfter = new TradeObjectTestMockData().getLeagueDataAfterTrade();
    ITradePersistence tradeDB = new TradeObjectTestMockData();
    ITeamInfo iTeamInfo = teamManagement.Team();
    IPlayerInfo iPlayerInfo = teamManagement.Player();
    Trade trade = new Trade(tradeDB);
    double randomOfferChance = leagueAfter.getGameConfig().getTrading().getRandomTradeOfferChance();

    Player player1 = teamManagement.PlayerWithParameters("Player One", "forward", true, 1, 1, 1, 1, 1, false,
            false, 0, false, 19, 5, 2000, Boolean.FALSE);
    Player player2 = teamManagement.PlayerWithParameters("Player Two", "defence", true, 1, 1, 1, 1, 1, false,
            false, 0, false, 19, 5, 2000, Boolean.FALSE);
    Player player1Offer = teamManagement.PlayerWithParameters("Player1", "Forward", false, 20, 1, 1, 1, 1, true, false,
            0, false, 19, 5, 2000, Boolean.FALSE);
    Player player2Offer = teamManagement.PlayerWithParameters("Player2", "Forward", false, 25, 1, 1, 1, 1, false, false,
            0, false, 19, 5, 2000, Boolean.FALSE);
    Player player1Request = teamManagement.PlayerWithParameters("Player3", "Forward", false, 26, 1, 19, 12, 1, false,
            false, 0, false, 19, 5, 2000, Boolean.FALSE);
    Player player2Request = teamManagement.PlayerWithParameters("Player4", "Forward", false, 33, 1, 17, 14, 1, false,
            false, 0, false, 19, 5, 2000, Boolean.FALSE);
    ArrayList<Player> playerList1 = new ArrayList<>();
    ArrayList<Player> playerList2 = new ArrayList<>();

    Trade trade1 = new Trade("Boston", playerList1, "Chicago", playerList2);
    private IUserOutput output = new CmdUserOutput();

    @Test
    public void parameterizedConstructorTest() {
        playerList1.add(player1Offer);
        playerList1.add(player2Offer);
        playerList2.add(player1Request);
        playerList2.add(player2Request);
        Assert.assertEquals("Boston", trade1.getTradeOfferTeam());
        Assert.assertEquals("Chicago", trade1.getTradeRequestedTeam());
        Assert.assertEquals(2, trade1.getPlayerListOfferTeam().size());
        Assert.assertEquals(2, trade1.getPlayerListRequestedTeam().size());
    }

    @Test
    public void getTradeOfferTeam() {
        Assert.assertEquals("Boston", trade1.getTradeOfferTeam());
    }

    @Test
    public void setTradeOfferTeam() {
        trade1.setTradeOfferTeam("New Test");
        Assert.assertEquals("New Test", trade1.getTradeOfferTeam());
    }

    @Test
    public void getTradeRequestedTeam() {
        Assert.assertEquals("Chicago", trade1.getTradeRequestedTeam());
    }

    @Test
    public void setTradeRequestedTeam() {
        trade1.setTradeRequestedTeam("test 2");
        Assert.assertEquals("test 2", trade1.getTradeRequestedTeam());
    }

    @Test
    public void getPlayerListOfferTeam() {
        playerList1.add(player1Offer);
        Assert.assertEquals(1, trade1.getPlayerListOfferTeam().size());
    }

    @Test
    public void setPlayerListOfferTeam() {
        playerList1.add(player1Offer);
        trade1.setPlayerListOfferTeam(playerList1);
        Assert.assertEquals(1, trade1.getPlayerListOfferTeam().size());
    }

    @Test
    public void getPlayerListRequestedTeam() {
        playerList2.add(player1Request);
        Assert.assertEquals(1, trade1.getPlayerListRequestedTeam().size());
    }

    @Test
    public void setPlayerListRequestedTeam() {
        playerList2.add(player1Request);
        trade1.setPlayerListRequestedTeam(playerList2);
        Assert.assertEquals(1, trade1.getPlayerListRequestedTeam().size());
    }

    @Test
    public void getWeakestPlayersTest() {
        List<Player> weakPlayer = new ArrayList<Player>();
        List<Player> weaKPlayer2 = new ArrayList<Player>();

        weakPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0)
                .getPlayerList().get(0));
        weakPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0)
                .getPlayerList().get(3));
        weaKPlayer2 = trade.getWeakestPlayers(leagueBefore.getGameConfig().getTrading().getMaxPlayersPerTrade(),
                "Boston", leagueBefore, iTeamInfo, iPlayerInfo);

        Assert.assertEquals(weakPlayer.size(), weaKPlayer2.size());
    }

    @Test
    public void getStrongestPlayersTest() {
        List<Player> weakPlayer = new ArrayList<Player>();
        weakPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0)
                .getPlayerList().get(0));
        weakPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0)
                .getPlayerList().get(3));
        trade.setTradeOfferTeam("Boston");
        trade.setPlayerListOfferTeam(weakPlayer);
        List<Player> strongPlayer = new ArrayList<Player>();
        List<Player> strongPlayer2;
        List<String> allTeamNames = new TradeObjectTestMockData().getAllTeamNames();
        strongPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0)
                .getPlayerList().get(1));
        strongPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0)
                .getPlayerList().get(2));
        strongPlayer2 = trade.getStrongestPlayers(trade, allTeamNames, leagueBefore, iTeamInfo, iPlayerInfo);

        Assert.assertEquals(strongPlayer.size(), strongPlayer2.size());
    }

    @Test
    public void startTradeTest() {
        try {
            League league = trade.startTrade(leagueBefore, null);
            Assert.assertEquals(leagueAfter.getLeagueName(), league.getLeagueName());
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            output.setOutput(e.getMessage());
            output.sendOutput();
        }
    }

    @Test
    public void checkRandOfferChanceTest() {
        Assert.assertEquals(Boolean.FALSE, trade.checkRandOfferChance(0));
    }

    @Test
    public void matchPositionTest() {
        String leagueName = TradeTestConstants.LEAGUE_NAME.toString();
        Assert.assertEquals(Boolean.TRUE, trade.matchPosition(leagueName, leagueName));
    }

    @Test
    public void sameTeamTest() {
        String leagueName = TradeTestConstants.LEAGUE_NAME.toString();
        Assert.assertEquals(Boolean.TRUE, trade.sameTeam(leagueName, leagueName));
    }

    @Test
    public void sortByStrengthTest() {
        HashMap<Integer, Double> hashMap = new HashMap<>();
        hashMap.put(1, 13.7);
        hashMap.put(10, 1.0);
        hashMap.put(3, 15.7);

        HashMap<Integer, Double> sortedHashMap = new HashMap<>();
        sortedHashMap.put(3, 15.7);
        sortedHashMap.put(1, 13.7);
        sortedHashMap.put(10, 1.0);

        Assert.assertEquals(sortedHashMap, trade.sortByStrength(hashMap, Boolean.TRUE));
    }

    @Test
    public void getPlayerOfSpecificType() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        List<Player> playerList1 = new ArrayList<>();
        playerList1.add(player1);
        playerList1.add(player2);
        String position = TradeTestConstants.FORWARD.toString();

        Assert.assertEquals(playerList.get(0).getPosition(), trade.getPlayersOfSpecificType(position, playerList1).get(0).getPosition());
    }

    @Test
    public void checkIfUnevenTradePossible() {
        playerList1.add(player1Offer);
        playerList1.add(player2Offer);
        playerList2.add(player1Request);
        playerList2.add(player2Request);
        trade1.setTradeRequestedTeam("Halifax");
        boolean b = trade1.checkIfUnevenTradePossible(trade1, leagueBefore, iTeamInfo, iPlayerInfo);
        Assert.assertEquals(Boolean.TRUE, trade1.checkIfUnevenTradePossible(trade1, leagueBefore, iTeamInfo, iPlayerInfo));
    }

    @Test
    public void getStrongestPlayerTest() {
        playerList1.add(player1Offer);
        playerList1.add(player2Offer);
        playerList2.add(player1Request);
        playerList2.add(player2Request);
        List<Player> playerList = trade1.getPlayerListOfferTeam();
        Assert.assertEquals(playerList.get(1), trade1.getStrongestPlayer(trade1.getPlayerListOfferTeam()));
    }

    @Test
    public void getAveragePlayerTest() {
        playerList1.add(player1Offer);
        playerList1.add(player2Offer);
        playerList2.add(player1Request);
        playerList2.add(player2Request);
        List<Player> playerList = trade1.getPlayerListOfferTeam();
        Assert.assertEquals(playerList.get(1), trade1.getAveragePlayer(trade1.getPlayerListOfferTeam()));
    }

    @Test
    public void pickToTradeForTest() {
        int pickMax = 20;
        Assert.assertEquals(Boolean.TRUE, trade1.pickToTradeFor(pickMax) < 20);
    }

    @Test
    public void startTradeDraftPickTest() {
        List<Conference> conferenceList = leagueBefore.getConferenceList();
        List<Team> teamList1 = new ArrayList<>();
        for (int index = 0; index < conferenceList.size(); index++) {
            List<Division> divisionList = conferenceList.get(index).getDivisionList();
            for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                List<Team> teamList = divisionList.get(dIndex).getTeamList();
                teamList1.addAll(teamList);
            }
        }
        Assert.assertEquals(teamList1.size(), trade1.startTradeDraftPick(leagueAfter, teamList1).size());

    }
}
