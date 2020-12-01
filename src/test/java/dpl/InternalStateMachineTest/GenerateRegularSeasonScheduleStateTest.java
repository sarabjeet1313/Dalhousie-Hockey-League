package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SimulationManagement.InternalStateMachine.GenerateRegularSeasonScheduleState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenerateRegularSeasonScheduleStateTest {
    private GenerateRegularSeasonScheduleState state;
    private IUserInput input;
    private IUserOutput output;
    private SeasonCalendar utility;
    private ISchedule schedule;
    private League leagueToSimulate;
    private InternalStateContext context;
    private IStandingsPersistance standingsDb;

    @Before
    public void setUp() throws Exception {
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        leagueToSimulate = LeagueMockData.getInstance().getTestData();
        utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        schedule = SystemConfig.getSingleInstance().getScheduleAbstractFactory().RegularSeasonSchedule(null, output);
        standingsDb = StandingsMockDb.getInstance();
        state = (GenerateRegularSeasonScheduleState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().GenerateRegularSeasonScheduleState(leagueToSimulate, output, 0, context, standingsDb, null, utility);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("AdvanceTime", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        assertFalse(state.getSchedule().getFinalSchedule().containsKey("02-10-2020"));
        state.doProcessing();
        assertTrue(state.getSchedule().getFinalSchedule().containsKey("02-10-2020"));
        assertEquals("Toronto", state.getSchedule().getFinalSchedule().get("02-10-2020").get(0).get("Boston"));
    }

    @Test
    public void getRegularSeasonEndDateTest() {
        assertEquals("03-04-2021", state.getRegularSeasonEndDate());
        assertNotEquals("12-11-2021", state.getRegularSeasonEndDate());
    }

    @Test
    public void getRegularSeasonStartDateTest() {
        assertEquals("30-09-2020", state.getRegularSeasonStartDate());
        assertNotEquals("13-11-2020", state.getRegularSeasonStartDate());
    }

    @Test
    public void getScheduleTest() {
        schedule = state.getSchedule();
        assertEquals("01-10-2020", schedule.getFirstDay());
        assertNotEquals("13-11-2020", schedule.getFirstDay());
    }

    @Test
    public void getStateNameTest() {
        assertEquals("GenerateRegularSeasonSchedule", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("AdvanceTime", state.getNextStateName());
        state.nextState(context);
        assertEquals("AdvanceTime", state.getNextStateName());
    }

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }

}