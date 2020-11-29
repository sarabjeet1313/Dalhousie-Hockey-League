package dpl.TradingTest;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.*;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeTest {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	League leagueBefore = new TradeObjectTestMockData().getLeagueData();
	League leagueAfter = new TradeObjectTestMockData().getLeagueDataAfterTrade();
	ITradePersistence tradeDB = new TradeObjectTestMockData();
	ITeamInfo IteamInfo = teamManagement.Team();
	IPlayerInfo IplayerInfo = teamManagement.Player();
	Trade trade = new Trade(tradeDB);
	private Player player1 = teamManagement.PlayerWithParameters("Player One", "forward", true, 1, 1, 1, 1, 1, false,
			false, 0, false, 19, 5, 2000, Boolean.FALSE);
	Player player1Offer = teamManagement.PlayerWithParameters("Player1", "Forward", false, 20, 1, 1, 1, 1, true, false,
			0, false, 19, 5, 2000, Boolean.FALSE);
	Player player2Offer = teamManagement.PlayerWithParameters("Player2", "Forward", false, 25, 1, 1, 1, 1, false, false,
			0, false, 19, 5, 2000, Boolean.FALSE);
	Player player1Request = teamManagement.PlayerWithParameters("Player3", "Forward", false, 26, 1, 19, 12, 1, false,
			false, 0, false, 19, 5, 2000, Boolean.FALSE);
	Player player2Request = teamManagement.PlayerWithParameters("Player4", "Forward", false, 33, 1, 17, 14, 1, false,
			false, 0, false, 19, 5, 2000, Boolean.FALSE);
	ArrayList<Player> playerList1 = new ArrayList<Player>();
	ArrayList<Player> playerList2 = new ArrayList<Player>();
	Trade t = new Trade("Boston", playerList1, "Chicago", playerList2);
	private IUserOutput output = new CmdUserOutput();

	@Test
	public void parameterizedConstructorTest() {
		playerList1.add(player1Offer);
		playerList1.add(player2Offer);
		playerList2.add(player1Request);
		playerList2.add(player2Request);
		Assert.assertEquals("Boston", t.getTradeOfferTeam());
		Assert.assertEquals("Chicago", t.getTradeRequestedTeam());
		Assert.assertEquals(2, t.getPlayerListOfferTeam().size());
		Assert.assertEquals(2, t.getPlayerListRequestedTeam().size());
	}

	@Test
	public void getTradeOfferTeam() {
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
		Assert.assertEquals("test 2", t.getTradeRequestedTeam());
	}

	@Test
	public void getPlayerListOfferTeam() {
		playerList1.add(player1Offer);
		Assert.assertEquals(1, t.getPlayerListOfferTeam().size());
	}

	@Test
	public void setPlayerListOfferTeam() {
		playerList1.add(player1Offer);
		t.setPlayerListOfferTeam(playerList1);
		Assert.assertEquals(1, t.getPlayerListOfferTeam().size());
	}

	@Test
	public void getPlayerListRequestedTeam() {
		playerList2.add(player1Request);
		Assert.assertEquals(1, t.getPlayerListRequestedTeam().size());
	}

	@Test
	public void setPlayerListRequestedTeam() {
		playerList2.add(player1Request);
		t.setPlayerListRequestedTeam(playerList2);
		Assert.assertEquals(1, t.getPlayerListRequestedTeam().size());
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
				"Boston", leagueBefore, IteamInfo, IplayerInfo);

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
		List<Player> strongPlayer2 = new ArrayList<Player>();
		List<String> allTeamNames = new TradeObjectTestMockData().getAllTeamNames();
		strongPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0)
				.getPlayerList().get(1));
		strongPlayer.add(leagueBefore.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0)
				.getPlayerList().get(2));
		strongPlayer2 = trade.getStrongestPlayers(trade, allTeamNames, leagueBefore, IteamInfo, IplayerInfo);

		Assert.assertEquals(strongPlayer.size(), strongPlayer2.size());
	}

	@Test
	public void startTradeTest() {
		try {
			League league = trade.startTrade(leagueBefore);
			Assert.assertEquals(leagueAfter.getLeagueName(), league.getLeagueName());
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

}
