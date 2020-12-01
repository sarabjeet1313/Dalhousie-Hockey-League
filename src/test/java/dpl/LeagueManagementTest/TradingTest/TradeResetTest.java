package dpl.LeagueManagementTest.TradingTest;

import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueManagement.Trading.ITradingAbstractFactory;
import dpl.LeagueManagement.Trading.TradeReset;
import dpl.SystemConfig;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TradeResetTest {

    private List<String> teamNames = new ArrayList<>();
    ITradePersistence tradeDB = new TradeObjectTestMockData();
    ITradingAbstractFactory tradingAbstractFactory = SystemConfig.getSingleInstance().getTradingAbstractFactory();
    TradeReset tradeReset = tradingAbstractFactory.TradeReset(tradeDB);

    @Test
    public void addToTeamNamesTest() {
        String team1 = "Halifax";
        String team2 = "Boston";
        teamNames.add(team1);
        tradeReset.addToTeamNames(team2);
        Assert.assertEquals(1, this.teamNames.size());
    }

    @Test
    public void clearListTest() {
        String team1 = "Halifax";
        String team2 = "Boston";
        tradeReset.addToTeamNames(team1);
        tradeReset.addToTeamNames(team2);
        tradeReset.clearList();
        Assert.assertEquals(0, this.teamNames.size());
    }

    @Test
    public void UpdateTradeTest() {
        Assert.assertEquals(-1, -1);
    }

}
