package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
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
    private StandingInfo standings;
    private ScheduleUtlity utility;
    private InternalStateContext context;
    private GeneratePlayoffScheduleState state;

    @Before
    public void setUp() throws Exception {
        leagueToSimulate = new LeagueMockData().getTestData();
        output = new CmdUserOutput();
        input = new CmdUserInput();
        standings = new StandingInfo(leagueToSimulate, 1);
        schedule = new PlayoffScheduleState(output, standings);
        utility = new ScheduleUtlity(1);
        context = new InternalStateContext(input, output);
        state = new GeneratePlayoffScheduleState(leagueToSimulate, utility, null, "13-11-2020", output, context);
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
        schedule = state.getSchedule();
        assertEquals("13-04-2022", schedule.getCurrentDay());
        assertNotEquals("11-13-2020", schedule.getCurrentDay());
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