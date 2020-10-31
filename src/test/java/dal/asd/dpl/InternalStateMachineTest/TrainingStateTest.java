package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.RegularSeasonScheduleState;
import dal.asd.dpl.Schedule.SeasonCalendar;
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

    @Before
    public void setUp() throws Exception {
        mockLeague = new LeagueMockData();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueToSimulate = mockLeague.getTestData();
        calendar = Calendar.getInstance();
        schedule = new RegularSeasonScheduleState(calendar, output);
        mockSchedule = new MockSchedule();
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        state = new TrainingState(leagueToSimulate, schedule, utility, "14-11-2020", output, context);
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
        // TODO
    }

    @Test
    public void anyUnplayedGamesTest() {
        assertTrue(state.anyUnplayedGames());
        // changing the current date to 13th.
        state = new TrainingState(leagueToSimulate, schedule, utility, "13-11-2020", output, context);
        assertFalse(state.anyUnplayedGames());
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