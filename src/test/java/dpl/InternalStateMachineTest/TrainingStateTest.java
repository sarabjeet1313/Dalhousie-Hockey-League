package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.SimulationManagement.InternalStateMachine.TrainingState;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.ScheduleTest.MockSchedule;
import dpl.SystemConfig;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
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
        mockLeague = LeagueMockData.getInstance();
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        leagueToSimulate = mockLeague.getTestData();
        calendar = Calendar.getInstance();
        schedule = SystemConfig.getSingleInstance().getScheduleAbstractFactory().RegularSeasonSchedule(calendar, output);
        mockSchedule = MockSchedule.getInstance();
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        training = SystemConfig.getSingleInstance().getGameplayConfigurationAbstractFactory().Training(100, 100);
        state = (TrainingState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().TrainingState(leagueToSimulate, training, schedule, utility, "14-11-2020", "", output, context, null, null, 0);
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

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }
}