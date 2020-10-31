package dal.asd.dpl.StandingsTest;

import dal.asd.dpl.Standings.StandingInfo;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandingInfoTest {

    private StandingInfo standings;
    private League leagueToSimulate;
    private StandingsMockDb standingsDb;
    private LeagueMockData leagueMock;

    @Before
    public void setUp() throws Exception {
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
    public void initializeStandingsTest() {
        assertNotEquals(10, standingsDb.getStandings().size());
        standings.initializeStandings();
        assertEquals(10, standingsDb.getStandings().size());
    }

    @Test
    public void updateStandingsTest() {
        assertNotEquals(1, standingsDb.getStandingsTeamWin().get("Boston").intValue());
        standings.updateTeamWinMap("Boston");
        standings.updateStandings();
        assertEquals(1, standingsDb.getStandingsTeamWin().get("Boston").intValue());
    }

}