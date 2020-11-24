package dpl.StandingsTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.TeamStanding;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeamStandingTest {

    private TeamStanding teamStanding;

    @Before
    public void setUp() throws Exception {
        teamStanding = new TeamStanding();
        teamStanding.setTeamName("Halifax");
        teamStanding.setWins(10);
        teamStanding.setLosses(10);
        teamStanding.setPoints(20);
    }

    @Test
    public void teamStandingTest() {
        TeamStanding teamStanding = new TeamStanding();
        assertTrue(teamStanding instanceof TeamStanding);
    }

    @Test
    public void getTeamNameTest() {
        assertNotEquals("Boston", teamStanding.getTeamName());
        teamStanding.setTeamName("Boston");
        assertEquals("Boston", teamStanding.getTeamName());
    }

    @Test
    public void setTeamNameTest() {
        assertNotEquals("Boston", teamStanding.getTeamName());
        teamStanding.setTeamName("Boston");
        assertEquals("Boston", teamStanding.getTeamName());
    }

    @Test
    public void getWinsTest() {
        assertNotEquals(12, teamStanding.getWins());
        teamStanding.setWins(12);
        assertEquals(12, teamStanding.getWins());
    }

    @Test
    public void setWinsTest() {
        assertNotEquals(12, teamStanding.getWins());
        teamStanding.setWins(12);
        assertEquals(12, teamStanding.getWins());
    }

    @Test
    public void getLosses() {
        assertNotEquals(14, teamStanding.getLosses());
        teamStanding.setLosses(14);
        assertEquals(14, teamStanding.getLosses());
    }

    @Test
    public void setLosses() {
        assertNotEquals(14, teamStanding.getLosses());
        teamStanding.setLosses(14);
        assertEquals(14, teamStanding.getLosses());
    }

    @Test
    public void getPoints() {
        assertNotEquals(24, teamStanding.getPoints());
        teamStanding.setPoints(24);
        assertEquals(24, teamStanding.getPoints());
    }

    @Test
    public void setPoints() {
        assertNotEquals(24, teamStanding.getPoints());
        teamStanding.setPoints(24);
        assertEquals(24, teamStanding.getPoints());
    }
}