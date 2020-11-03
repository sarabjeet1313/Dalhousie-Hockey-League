package dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachineTest;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.*;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.ISchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.RegularSeasonSchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

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
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueToSimulate = new LeagueMockData().getTestData();
        utility = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
        schedule = new RegularSeasonSchedule(null, output);
        standingsDb = new StandingsMockDb(0);
        state = new GenerateRegularSeasonScheduleState(leagueToSimulate, output, 0, context, standingsDb);
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
//        assertFalse(state.getSchedule().getFinalSchedule().containsKey("02-10-2020"));
//        state.doProcessing();
//        assertTrue(state.getSchedule().getFinalSchedule().containsKey("02-10-2020"));
//        assertEquals("Halifax", state.getSchedule().getFinalSchedule().get("02-10-2020").get(0).get("Boston"));
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
}