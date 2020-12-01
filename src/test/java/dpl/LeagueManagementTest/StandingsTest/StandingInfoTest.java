package dpl.LeagueManagementTest.StandingsTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;

public class StandingInfoTest {

	private StandingInfo standings;
	private League leagueToSimulate;
	private StandingsMockDb standingsDb;
	private LeagueMockData leagueMock;
	private IUserOutput output;

	@Before
	public void setUp() throws Exception {
		output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
		standingsDb = StandingsMockDb.getInstance();
		leagueToSimulate = LeagueMockData.getInstance().getTestData();
		standings = SystemConfig.getSingleInstance().getStandingsAbstractFactory().StandingInfo(leagueToSimulate, 0, standingsDb, output);
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
	public void getStandingTest() {
		assertNotEquals(1, standings.getStanding().getSeason());
		assertEquals(0, standings.getStanding().getSeason());
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
	public void showStatsTest() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		standings.showStats();
		String expected  = "Goals per Game : 0.0Penalties per Game : 0.0Shots : 0.0Saves : 0.0";
		String gotOutput = out.toString().replaceAll("\n", "");
		gotOutput = gotOutput.replaceAll("\r", "");
		assertEquals(expected, gotOutput);
	}

	@Test
	public void sortMapDraftTest() {
		standings.sortMapDraft();
		assertFalse(standings.getTeamWinMap().containsKey("Boston"));
		standings.updateTeamWinMap("Halifax");
		assertTrue(standings.getTeamWinMap().containsKey("Halifax"));
	}

}