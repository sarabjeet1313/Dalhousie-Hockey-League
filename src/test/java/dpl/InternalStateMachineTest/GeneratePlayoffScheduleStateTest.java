package dpl.InternalStateMachineTest;

import dpl.InternalStateMachine.*;
import dpl.Schedule.ISchedule;
import dpl.Schedule.PlayoffSchedule;
import dpl.Schedule.SeasonCalendar;
import dpl.Standings.IStandingsPersistance;
import dpl.StandingsTest.StandingsMockDb;
import dpl.TeamManagement.League;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeneratePlayoffScheduleStateTest {

    private League leagueToSimulate;
    private IUserOutput output;
    private IUserInput input;
    private ISchedule schedule;
    private IStandingsPersistance standings;
    private SeasonCalendar utility;
    private InternalStateContext context;
    private GeneratePlayoffScheduleState state;

    @Before
    public void setUp() throws Exception {
        leagueToSimulate = new LeagueMockData().getTestData();
        output = new CmdUserOutput();
        input = new CmdUserInput();
        standings = new StandingsMockDb(0);
        schedule = new PlayoffSchedule(output, standings, 0);
        utility = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
        state = new GeneratePlayoffScheduleState(leagueToSimulate, utility, standings, output, context, 1);
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
        assertFalse(state.getSchedule().getFinalSchedule().containsKey("15-04-2021"));
        state.doProcessing();
        assertTrue(state.getSchedule().getFinalSchedule().containsKey("15-04-2021"));
        assertEquals("Halifax", state.getSchedule().getFinalSchedule().get("15-04-2021").get(0).get("Boston"));
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
        assertEquals(1, schedule.getSeasonType());
        schedule.setSeasonType(0);
        assertNotEquals(1, schedule.getSeasonType());
    }
}