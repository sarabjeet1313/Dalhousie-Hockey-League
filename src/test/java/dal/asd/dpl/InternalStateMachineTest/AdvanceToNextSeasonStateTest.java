package dal.asd.dpl.InternalStateMachineTest;
import dal.asd.dpl.InternalStateMachine.AdvanceToNextSeasonState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdvanceToNextSeasonStateTest {

    private IUserInput input;
    private IUserOutput output;
    private SeasonCalendar utility;
    private InternalStateContext context;
    private AdvanceToNextSeasonState state;
    private League leagueToSimulate;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueToSimulate = new LeagueMockData().getTestData();
        utility = new SeasonCalendar(0, output);
        utility.setLastSeasonDay("20-11-2020");
        context = new InternalStateContext(input, output);
        state = new AdvanceToNextSeasonState(leagueToSimulate, context, utility, "13-11-2020", output);
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("Persist", state.getNextStateName());
        state.nextState(context);
        assertEquals("Persist", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue( state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getUpdatedLeagueTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue( state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("NextSeason", state.getStateName());
        assertNotEquals("Training", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("Persist", state.getNextStateName());
        state.nextState(context);
        assertEquals("Persist", state.getNextStateName());
    }
}