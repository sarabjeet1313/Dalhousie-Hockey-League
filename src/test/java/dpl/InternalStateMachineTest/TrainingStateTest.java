package dpl.InternalStateMachineTest;

import dpl.GameplayConfiguration.Training;
import dpl.InternalStateMachine.*;
import dpl.Schedule.ISchedule;
import dpl.Schedule.RegularSeasonSchedule;
import dpl.Schedule.SeasonCalendar;
import dpl.ScheduleTest.MockSchedule;
import dpl.TeamManagement.League;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class TrainingStateTest {

    private TrainingState state;
    private League leagueToSimulate;
    private LeagueMockData mockLeague;
    private ISchedule schedule;
    private MockSchedule mockSchedule;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private IUserOutput output;
    private IUserInput input;
    private Calendar calendar;
    private Training training;

    @Before
    public void setUp() throws Exception {
        mockLeague = new LeagueMockData();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueToSimulate = mockLeague.getTestData();
        calendar = Calendar.getInstance();
        schedule = new RegularSeasonSchedule(calendar, output);
        mockSchedule = new MockSchedule();
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        training = new Training(100, 100);
        state = new TrainingState(leagueToSimulate, training, schedule, utility, "14-11-2020", output, context);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("SimulateGame", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getUpdatedLeagueTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Training", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("SimulateGame", state.getNextStateName());
        state.nextState(context);
        assertEquals("SimulateGame", state.getNextStateName());
    }
}