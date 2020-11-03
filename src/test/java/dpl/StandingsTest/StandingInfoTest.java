package dpl.StandingsTest;

import dpl.Standings.StandingInfo;
import dpl.TeamManagement.League;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.sql.SQLException;

public class StandingInfoTest {

	private StandingInfo standings;
	private League leagueToSimulate;
	private StandingsMockDb standingsDb;
	private LeagueMockData leagueMock;
	private IUserOutput output;

	@Before
	public void setUp() throws Exception {
		output = new CmdUserOutput();
		standingsDb = new StandingsMockDb(0);
		leagueToSimulate = new LeagueMockData().getTestData();
		standings = new StandingInfo(leagueToSimulate, 0, standingsDb);
	}

	@Test
	public void updateTeamWinMapTest() {
		assertFalse(standings.getTeamWinMap().containsKey("Boston"));
		standings.updateTeamWinMap("Boston");
		assertTrue(standings.getTeamWinMap().containsKey("Boston"));
	}

	@Test
	public void updateTeamLoseMapTest() {
		assertFalse(standings.getTeamLoseMap().containsKey("Halifax"));
		standings.updateTeamLoseMap("Halifax");
		assertTrue(standings.getTeamLoseMap().containsKey("Halifax"));
	}

	@Test
	public void getTeamWinMapTest() {
		assertFalse(standings.getTeamWinMap().containsKey("Boston"));
		standings.updateTeamWinMap("Boston");
		assertTrue(standings.getTeamWinMap().containsKey("Boston"));
	}

	@Test
	public void getTeamLoseMapTest() {
		assertFalse(standings.getTeamLoseMap().containsKey("Halifax"));
		standings.updateTeamLoseMap("Halifax");
		assertTrue(standings.getTeamLoseMap().containsKey("Halifax"));
	}

	@Test
	public void updateStandingsTest() {
		try {
			assertNotEquals(1, standingsDb.getStandingsTeamWin().get("Boston").intValue());
			standings.updateTeamWinMap("Boston");
			standings.updateStandings();
			assertEquals(1, standingsDb.getStandingsTeamWin().get("Boston").intValue());
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

}