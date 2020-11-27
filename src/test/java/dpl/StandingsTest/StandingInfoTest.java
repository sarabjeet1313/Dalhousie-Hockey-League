package dpl.StandingsTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

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
		standings = new StandingInfo(leagueToSimulate, 0, standingsDb, output);
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

	@Test
	public void getTopDivisionTeamsTest() {
		standings.updateTeamWinMap("Boston");
		List<String> teams = standings.getTopDivisionTeams(leagueToSimulate, "Atlantic");
		assertTrue(teams.contains("Boston"));
	}

	@Test
	public void resetStatsTest() {
		assertEquals(0, standings.getTotalGoalsInSeason(), 0.5);
		assertEquals(0, standings.getTotalSavesInSeason(), 0.5);
	}

	@Test
	public void getTotalSeasonMatchesTest() {
		assertNotEquals(10, standings.getTotalSeasonMatches(), 0.5);
		standings.setTotalSeasonMatches(10);
		assertEquals(10, standings.getTotalSeasonMatches(), 0.5);
	}

	@Test
	public void setTotalSeasonMatchesTest() {
		assertNotEquals(10, standings.getTotalSeasonMatches(), 0.5);
		standings.setTotalSeasonMatches(10);
		assertEquals(10, standings.getTotalSeasonMatches(), 0.5);
	}

	@Test
	public void getTotalGoalsInSeasonTest() {
		assertNotEquals(20, standings.getTotalGoalsInSeason(), 0.5);
		standings.setTotalGoalsInSeason(20);
		assertEquals(20, standings.getTotalGoalsInSeason(), 0.5);
	}

	@Test
	public void setTotalGoalsInSeasonTest() {
		assertNotEquals(20, standings.getTotalGoalsInSeason(), 0.5);
		standings.setTotalGoalsInSeason(20);
		assertEquals(20, standings.getTotalGoalsInSeason(), 0.5);
	}

	@Test
	public void getTotalPenaltiesInSeasonTest() {
		assertNotEquals(30, standings.getTotalPenaltiesInSeason(), 0.5);
		standings.setTotalPenaltiesInSeason(30);
		assertEquals(30, standings.getTotalPenaltiesInSeason(), 0.5);
	}

	@Test
	public void setTotalPenaltiesInSeasonTest() {
		assertNotEquals(30, standings.getTotalPenaltiesInSeason(), 0.5);
		standings.setTotalPenaltiesInSeason(30);
		assertEquals(30, standings.getTotalPenaltiesInSeason(),0.5);
	}

	@Test
	public void getTotalShotsInSeasonTest() {
		assertNotEquals(40, standings.getTotalShotsInSeason(), 0.5);
		standings.setTotalShotsInSeason(40);
		assertEquals(40, standings.getTotalShotsInSeason(), 0.5);
	}

	@Test
	public void setTotalShotsInSeasonTest() {
		assertNotEquals(40, standings.getTotalShotsInSeason(), 0.5);
		standings.setTotalShotsInSeason(40);
		assertEquals(40, standings.getTotalShotsInSeason(), 0.5);
	}

	@Test
	public void getTotalSavesInSeasonTest() {
		assertNotEquals(50, standings.getTotalSavesInSeason(), 0.5);
		standings.setTotalSavesInSeason(50);
		assertEquals(50, standings.getTotalSavesInSeason(), 0.5);
	}

	@Test
	public void setTotalSavesInSeasonTest() {
		assertNotEquals(50, standings.getTotalSavesInSeason(), 0.5);
		standings.setTotalSavesInSeason(50);
		assertEquals(50, standings.getTotalSavesInSeason(), 0.5);
	}

	@Test
	public void initializeStandingsTest() {
		try {
			assertTrue(standings.initializeStandings());
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	@Test
	public void showStatsTest() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		standings.showStats();
		String expected  = "Goals per Game : 0.0Penalties per Game : 0.0Shots : 0.0Saves : 0.0";
		String gotOutput = out.toString().replaceAll("\n", "");
		gotOutput = gotOutput.replaceAll("\r", "");
		assertEquals(expected, gotOutput);
	}

}