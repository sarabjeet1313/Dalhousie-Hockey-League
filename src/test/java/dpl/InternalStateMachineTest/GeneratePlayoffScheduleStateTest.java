package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.*;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.ScheduleTest.MockSchedule;
import dpl.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeneratePlayoffScheduleStateTest {

    private League leagueToSimulate;
    private IUserOutput output;
    private IUserInput input;
    private ISchedule schedule;
    private IStandingsPersistance standingsDb;
    private StandingInfo standings;
    private SeasonCalendar utility;
    private InternalStateContext context;
    private GeneratePlayoffScheduleState state;

    @Before
    public void setUp() throws Exception {
        leagueToSimulate = new LeagueMockData().getTestData();
        output = new CmdUserOutput();
        input = new CmdUserInput();
        standingsDb = new StandingsMockDb(0);
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb, output);
        schedule = new MockSchedule();
        utility = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
        state = new GeneratePlayoffScheduleState(leagueToSimulate, utility, standingsDb, standings, output, context, 1, "", "");
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Training", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.setSchedule(schedule);
        assertFalse(state.getSchedule().getFinalSchedule().containsKey("15-11-2020"));
        state.doProcessing();
        assertTrue(state.getSchedule().getFinalSchedule().containsKey("14-11-2020"));
        assertEquals("Halifax", state.getSchedule().getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }

    @Test
    public void getStateNameTest() {
        assertEquals("GeneratePlayoffSchedule", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertNotEquals("GeneratePlayoffSchedule", state.getNextStateName());
        assertEquals("Training", state.getNextStateName());
    }

    @Test
    public void getScheduleTest() {
        assertEquals(0, schedule.getSeasonType());
        schedule.setSeasonType(1);
        assertNotEquals(0, schedule.getSeasonType());
    }

    @Test
    public void setScheduleTest() {
        assertFalse(state.getSchedule().getFinalSchedule().containsKey("14-11-2020"));
        state.setSchedule(schedule);
        assertTrue(state.getSchedule().getFinalSchedule().containsKey("14-11-2020"));

    }
}