package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.PlayoffSchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.StandingsTest.StandingsMockDb;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
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
        state = new GeneratePlayoffScheduleState(leagueToSimulate, utility, standings, "13-11-2020", output, context, 1);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Training", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    //TODO TEST
//    @Test
//    public void doProcessingTest() {
//        assertFalse(state.getSchedule().getFinalSchedule().containsKey("15-04-2021"));
//        state.doProcessing();
//        assertTrue(state.getSchedule().getFinalSchedule().containsKey("15-04-2021"));
//        assertEquals("Halifax", state.getSchedule().getFinalSchedule().get("15-04-2021").get(0).get("Boston"));
//    }

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