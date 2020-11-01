package dal.asd.dpl.ScheduleTest;

import dal.asd.dpl.StandingsTest.StandingsMockDb;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.Schedule.RegularSeasonSchedule;
import dal.asd.dpl.Standings.StandingInfo;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import static org.junit.Assert.*;

public class RegularSeasonScheduleTest {

    private RegularSeasonSchedule state;
    private IUserOutput output;
    private StandingInfo standings;
    private League leagueToSimulate;
    private MockSchedule mockSchedule;
    private Calendar calendar;
    private IStandingsPersistance standingsDb;

    @Before
    public void setUp() throws Exception {
        output = new CmdUserOutput();
        leagueToSimulate = new LeagueMockData().getTestData();
        standingsDb = new StandingsMockDb(0);
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb);
        calendar = Calendar.getInstance();
        state = new RegularSeasonSchedule(calendar, output);
        mockSchedule = new MockSchedule();
    }

    @Test
    public void getSeasonTypeTest() {
        assertEquals(0, state.getSeasonType());
        state.setSeasonType(2);
        assertNotEquals(1,state.getSeasonType());
        assertEquals(2, state.getSeasonType());
    }

    @Test
    public void setSeasonTypeTest() {
        assertEquals(0, state.getSeasonType());
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

//    @Test
//    public void generateScheduleTest() {
//        state.setCurrentDay("13-11-2020");
//        state.setFirstDay("14-11-2020");
//        state.setLastDay("20-11-2020");
//        state.generateSchedule(leagueToSimulate);
//        assertEquals("Halifax", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
//        assertNotEquals("Toronto", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
//    }

    @Test
    public void incrementCurrentDayTest() {
        state.setCurrentDay("13-11-2020");
        assertNotEquals("14-11-2020", state.getCurrentDay());
        state.incrementCurrentDay();
        assertEquals("14-11-2020", state.getCurrentDay());
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
    public void getFinalScheduleTest() {
        state.setFinalSchedule(mockSchedule.getMockSchedule());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        assertEquals("Halifax", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Calgary", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }

    @Test
    public void setFinalScheduleTest() {
        state.setFinalSchedule(mockSchedule.getMockSchedule());
        assertEquals("Halifax", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Calgary", state.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }
}