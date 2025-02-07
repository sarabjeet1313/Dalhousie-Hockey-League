package dpl.ScheduleTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.PlayoffSchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.StandingsTest.StandingsMockDb;
import dpl.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PlayoffScheduleTest {

    private PlayoffSchedule state;
    private IUserOutput output;
    private StandingInfo standings;
    private League leagueToSimulate;
    private MockSchedule mockSchedule;
    private IStandingsPersistance standingsDb;

    @Before
    public void setUp() throws Exception {
        output = new CmdUserOutput();
        standingsDb = new StandingsMockDb(0);
        leagueToSimulate = new LeagueMockData().getTestData();
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb, output);
        state = new PlayoffSchedule(output, standingsDb, standings, 0);
        mockSchedule = new MockSchedule();
    }

    @Test
    public void getSeasonTypeTest() {
        assertEquals(1, state.getSeasonType());
        state.setSeasonType(2);
        assertNotEquals(1,state.getSeasonType());
        assertEquals(2, state.getSeasonType());
    }

    @Test
    public void setSeasonTypeTest() {
        assertEquals(1, state.getSeasonType());
        state.setSeasonType(2);
        assertNotEquals(1,state.getSeasonType());
        assertEquals(2, state.getSeasonType());
    }

    @Test
    public void getFirstDayTest() {
        assertNotEquals("13-11-2020", state.getFirstDay());
        state.setFirstDay("13-11-2020");
        assertEquals("13-11-2020", state.getFirstDay());
    }

    @Test
    public void setFirstDayTest() {
        assertNotEquals("13-11-2020", state.getFirstDay());
        state.setFirstDay("13-11-2020");
        assertEquals("13-11-2020", state.getFirstDay());
    }

    @Test
    public void getLastDayTest() {
        assertNotEquals("31-12-2022", state.getLastDay());
        state.setLastDay("31-12-2022");
        assertEquals("31-12-2022", state.getLastDay());
    }

    @Test
    public void setLastDayTest() {
        assertNotEquals("31-12-2022", state.getLastDay());
        state.setLastDay("31-12-2022");
        assertEquals("31-12-2022", state.getLastDay());
    }

    @Test
    public void getCurrentDayTest() {
        assertNotEquals("29-10-2020", state.getCurrentDay());
        state.setCurrentDay("29-10-2020");
        assertEquals("29-10-2020", state.getCurrentDay());
    }

    @Test
    public void setCurrentDayTest() {
        assertNotEquals("29-10-2020", state.getCurrentDay());
        state.setCurrentDay("29-10-2020");
        assertEquals("29-10-2020", state.getCurrentDay());
    }

    @Test
    public void setTeamsToBeScheduledTest() {
        assertNotEquals(1, state.getTeamsToBeScheduled().size());
        state.setTeamsToBeScheduled(mockSchedule.getMockToBeScheduledTeams());
        assertEquals(1, state.getTeamsToBeScheduled().size());
        assertTrue(state.getTeamsToBeScheduled().contains("Brampton"));
    }

    @Test
    public void getTeamsToBeScheduledTest() {
        assertNotEquals(1, state.getTeamsToBeScheduled().size());
        state.setTeamsToBeScheduled(mockSchedule.getMockToBeScheduledTeams());
        assertEquals(1, state.getTeamsToBeScheduled().size());
        assertTrue(state.getTeamsToBeScheduled().contains("Brampton"));
    }

    @Test
    public void setTeamsScheduledTest() {
        assertNotEquals(2, state.getTeamsScheduled().size());
        state.setTeamsScheduled(mockSchedule.getMockScheduledTeams());
        assertEquals(2, state.getTeamsScheduled().size());
        assertTrue(state.getTeamsScheduled().contains("Halifax"));
    }

    @Test
    public void getTeamsScheduledTest() {
        assertNotEquals(2, state.getTeamsScheduled().size());
        state.setTeamsScheduled(mockSchedule.getMockScheduledTeams());
        assertEquals(2, state.getTeamsScheduled().size());
        assertTrue(state.getTeamsScheduled().contains("Halifax"));
    }

    @Test
    public void generateScheduleTest() {
        state.setCurrentDay("13-11-2020");
        state.setFirstDay("13-11-2020");
        state.setLastDay("20-11-2020");
        state.generateSchedule(leagueToSimulate);
        state.setFinalSchedule(mockSchedule.getFinalSchedule());
        assertEquals("Halifax", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Toronto", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }

    @Test
    public void generateScheduleOnTheFlyTest() {
        state.setFirstDay("13-11-2020");
        state.setLastDay("20-11-2020");
        state.generateScheduleOnTheFly(mockSchedule.getMockScheduledTeams(), "13-11-2020");
        assertEquals("Halifax", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Toronto", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }

    @Test
    public void incrementCurrentDayTest() {
        state.setCurrentDay("13-11-2020");
        assertNotEquals("14-11-2020", state.getCurrentDay());
        state.incrementCurrentDay();
        assertEquals("14-11-2020", state.getCurrentDay());
    }

    @Test
    public void getFinalScheduleTest() {
        state.setFirstDay("13-11-2020");
        state.setLastDay("20-11-2020");
        state.generateScheduleOnTheFly(mockSchedule.getMockScheduledTeams(), "13-11-2020");
        assertEquals("Halifax", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Toronto", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }

    @Test
    public void setFinalScheduleTest() {
        state.setFinalSchedule(mockSchedule.getMockSchedule());
        assertEquals("Halifax", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Calgary", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }

    @Test
    public void anyUnplayedGameTest() {
        state.setFinalSchedule(mockSchedule.getMockSchedule());
        assertTrue(state.anyUnplayedGame("14-11-2020"));
        assertFalse(state.anyUnplayedGame("17-11-2020"));
    }

}