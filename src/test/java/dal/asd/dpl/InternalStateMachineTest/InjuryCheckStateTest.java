package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.RegularSeasonSchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.ScheduleTest.MockSchedule;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class InjuryCheckStateTest {
    private IUserInput input;
    private IUserOutput output;
    private League leagueToSimulate;
    private ISchedule schedule;
    private MockSchedule mockSchedule;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private InjuryCheckState state;
    private Calendar calendar;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        calendar = Calendar.getInstance();
        schedule = new RegularSeasonSchedule(calendar, output);
        mockSchedule = new MockSchedule();
        leagueToSimulate = new LeagueMockData().getTestData();
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        state = new InjuryCheckState(leagueToSimulate, schedule, context, utility, "14-11-2020", output);
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("Trading", state.getNextStateName());
        state.nextState(context);
        assertEquals("SimulateGame", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Injury", state.getStateName());
        assertNotEquals("Trading", state.getStateName());
    }

    @Test
    public void getUpdatedLeague() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue( state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("Trading", state.getNextStateName());
        state.nextState(context);
        assertEquals("SimulateGame", state.getNextStateName());
    }
}