package dpl.LeagueManagementTest.StandingsTest;

import dpl.LeagueManagement.Standings.Standing;
import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandingTest {
    private Standing standing;
    private StandingsMockDb standingsMock;

    @Before
    public void setUp() throws Exception {
        standing = SystemConfig.getSingleInstance().getStandingsAbstractFactory().Standing();
        standingsMock = StandingsMockDb.getInstance();
        standing.setSeason(1);
        standing.setStandings(standingsMock.getTeamStandings());
    }

    @Test
    public void standingTest() {
        Standing standing = SystemConfig.getSingleInstance().getStandingsAbstractFactory().Standing();
        assertTrue(standing instanceof Standing);
    }

    @Test
    public void getSeasonTest() {
        assertNotEquals(2, standing.getSeason());
        standing.setSeason(2);
        assertEquals(2, standing.getSeason());
    }

    @Test
    public void setSeasonTest() {
        assertNotEquals(2, standing.getSeason());
        standing.setSeason(2);
        assertEquals(2, standing.getSeason());
    }

    @Test
    public void getStandingsTest() {
        assertNotEquals("Halifax", standing.getStandings().get(0).getTeamName());
        standing.setStandings(standingsMock.getTeamStandings());
        assertEquals("Boston", standing.getStandings().get(0).getTeamName());
    }

    @Test
    public void setStandingsTest() {
        assertNotEquals("Halifax", standing.getStandings().get(0).getTeamName());
        standing.setStandings(standingsMock.getTeamStandings());
        assertEquals("Boston", standing.getStandings().get(0).getTeamName());
    }

    @Test
    public void updateStandingsWinTest() {
        assertNotEquals(34, standing.getStandings().get(2).getWins());
        standing.updateStandingsWin("Brampton");
        assertEquals(34, standing.getStandings().get(2).getWins());
    }

    @Test
    public void updateStandingsLossesTest() {
        assertNotEquals(14, standing.getStandings().get(1).getLosses());
        standing.updateStandingsLosses("Toronto");
        assertEquals(14, standing.getStandings().get(1).getLosses());
    }
}