package dpl.TradingTest;

import dpl.TeamManagement.*;
import dpl.Trading.ITradePersistence;
import dpl.Trading.Trade;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TradeTest {

    League leagueBefore = new TradeObjectTestMockData().getLeagueData();
    League leagueAfter = new TradeObjectTestMockData().getLeagueDataAfterTrade();
    ITradePersistence tradeDB = new TradeObjectTestMockData();
    ITeamInfo IteamInfo = new Team();
    IPlayerInfo IplayerInfo = new Player();
    Trade trade = new Trade(tradeDB);
    private Player player1 = new Player("Player One", "forward", true, 1, 1, 1, 1, 1, false, false, 0);
    Player player1Offer = new Player("Player1", "Forward", false,20, 1, 1, 1, 1, true,false, 0);
    Player player2Offer = new Player("Player2", "Forward", false, 25 ,1, 1, 1, 1, false, false, 0);
    Player player1Request = new Player("Player3", "Forward", false, 26, 1, 19, 12, 1, false, false,0);
    Player player2Request = new Player("Player4", "Forward", false,33 , 1, 17, 14, 1, false, false, 0);
    ArrayList<Player> playerList1 = new ArrayList<Player>();
    ArrayList<Player> playerList2 = new ArrayList<Player>();
    Trade t = new Trade("Boston",playerList1,"Chicago",playerList2);

    @Test
    public void parameterizedConstructorTest() {
        playerList1.add(player1Offer);
        playerList1.add(player2Offer);
        playerList2.add(player1Request);
        playerList2.add(player2Request);
        Assert.assertEquals("Boston", t.getTradeOfferTeam());
        Assert.assertEquals("Chicago",t.getTradeRequestedTeam());
        Assert.assertEquals(2, t.getPlayerListOfferTeam().size());
        Assert.assertEquals(2, t.getPlayerListRequestedTeam().size());
    }

    @Test
    public void getTradeOfferTeam(){
        Assert.assertEquals("Boston", t.getTradeOfferTeam());
    }

    @Test
    public void setTradeOfferTeam() {
        t.setTradeOfferTeam("New Test");
        Assert.assertEquals("New Test", t.getTradeOfferTeam());
    }

    @Test
    public void getTradeRequestedTeam() {
        Assert.assertEquals("Chicago", t.getTradeRequestedTeam());
    }

    @Test
    public void setTradeRequestedTeam() {
        t.setTradeRequestedTeam("test 2");
        Assert.assertEquals("test 2",t.getTradeRequestedTeam());
    }

    @Test
    public void getPlayerListOfferTeam() {
        playerList1.add(player1Offer);
        Assert.assertEquals(1,t.getPlayerListOfferTeam().size());
    }

    @Test
    public void setPlayerListOfferTeam() {
        playerList1.add(player1Offer);
        t.setPlayerListOfferTeam(playerList1);
        Assert.assertEquals(1,t.getPlayerListOfferTeam().size());
    }

    @Test
    public void getPlayerListRequestedTeam() {
        playerList2.add(player1Request);
        Assert.assertEquals(1,t.getPlayerListRequestedTeam().size());
    }

    @Test
    public void setPlayerListRequestedTeam() {
        playerList2.add(player1Request);
        t.setPlayerListRequestedTeam(playerList2);
        Assert.assertEquals(1,t.getPlayerListRequestedTeam().size());
    }

    @Test
    public void getWeakestPlayersTest(){
        List<Player> weakPlayer= new ArrayList<Player>();
        List<Player> weaKPlayer2 = new ArrayList<Player>();

        weakPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getPlayerList().get(0));
        weaKPlayer2= trade.getWeakestPlayers(tradeDB.getMaxPlayersPerTrade(),"Boston", leagueBefore, IteamInfo, IplayerInfo);

        Assert.assertEquals(weakPlayer.size()+1, weaKPlayer2.size());
    }

    @Test
    public void startTradeTest(){
        League league = trade.startTrade(leagueBefore);
//        IUserInput input = new CmdUserInput();
//        String inp = "y";
//        InputStream in = new ByteArrayInputStream(inp.getBytes());
//        System.setIn(in);
//        input.setInput();
        Assert.assertEquals(leagueAfter.getLeagueName() , trade.startTrade(leagueBefore).getLeagueName());

    }


}
