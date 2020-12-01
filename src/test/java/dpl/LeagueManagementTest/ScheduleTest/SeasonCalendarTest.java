package dpl.LeagueManagementTest.ScheduleTest;

import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeasonCalendarTest {

    private SeasonCalendar state;
    private IUserOutput output;

    @Before
    public void setUp() throws Exception {
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        state = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
    }

    @Test
    public void getRegularSeasonStartDayTest() {
        assertNotEquals("31-09-2020", state.getRegularSeasonStartDay());
        assertEquals("30-09-2020", state.getRegularSeasonStartDay());
    }

    @Test
    public void getRegularSeasonFirstDayTest() {
        assertNotEquals("31-09-2020", state.getRegularSeasonFirstDay());
        assertEquals("01-10-2020", state.getRegularSeasonFirstDay());
    }

    @Test
    public void getPlayoffFirstDayTest() {
        assertNotEquals("31-09-2020", state.getPlayoffFirstDay());
        assertEquals("14-04-2021", state.getPlayoffFirstDay());
    }

    @Test
    public void getRegularSeasonLastDayTest() {
        assertNotEquals("31-09-2020", state.getRegularSeasonLastDay());
        assertEquals("03-04-2021", state.getRegularSeasonLastDay());
    }

    @Test
    public void getPlayoffLastDayTest() {
        assertNotEquals("31-09-2020", state.getPlayoffLastDay());
        assertEquals("01-06-2021", state.getPlayoffLastDay());
    }

    @Test
    public void getNextRegularSeasonStartDayTest() {
        assertNotEquals("31-09-2020", state.getNextRegularSeasonStartDay());
        assertEquals("30-09-2021", state.getNextRegularSeasonStartDay());
    }

    @Test
    public void getSeasonWinnerTest() {
        assertNotEquals("Sarab's Team", state.getSeasonWinner());
        state.setSeasonWinner("Sarab's Team");
        assertEquals("Sarab's Team", state.getSeasonWinner());
    }

    @Test
    public void setSeasonWinnerTest() {
        assertNotEquals("Sarab's Team", state.getSeasonWinner());
        state.setSeasonWinner("Sarab's Team");
        assertEquals("Sarab's Team", state.getSeasonWinner());
    }

    @Test
    public void setSeasonOverStatusTest() {
        assertFalse(state.getSeasonOverStatus());
        state.setSeasonOverStatus(true);
        assertTrue(state.getSeasonOverStatus());
    }

    @Test
    public void getSeasonOverStatusTest() {
        assertFalse(state.getSeasonOverStatus());
        state.setSeasonOverStatus(true);
        assertTrue(state.getSeasonOverStatus());
    }

    @Test
    public void setLastSeasonDayTest() {
        assertNotEquals("21-09-2021", state.getLastSeasonDay());
        state.setLastSeasonDay("21-09-2021");
        assertEquals("21-09-2021", state.getLastSeasonDay());
    }

    @Test
    public void getLastSeasonDayTest() {
        assertNotEquals("21-09-2021", state.getLastSeasonDay());
        state.setLastSeasonDay("21-09-2021");
        assertEquals("21-09-2021", state.getLastSeasonDay());
    }

    @Test
    public void getAllStartGameDayTest() {
        assertNotEquals("29-01-2021", state.getAllStartGameDay());
        assertEquals("23-01-2021", state.getAllStartGameDay());
    }

    @Test
    public void isTradeDeadlinePendingTest() {
        try {
            assertTrue(state.isTradeDeadlinePending("30-11-2020"));
            assertFalse(state.isTradeDeadlinePending("30-06-2021"));
        } catch (Exception e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        }
    }

    @Test
    public void isLastDayOfSeasonTest() {
        try {
            assertTrue(state.isLastDayOfSeason("01-06-2021"));
            assertFalse(state.isLastDayOfSeason("30-06-2021"));
        } catch (Exception e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        }

    }
}