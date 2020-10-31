package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AgingStateTest {

    private InternalStateContext context;
    private SeasonCalendar utility;
    private IUserOutput output;
    private IUserInput input;
    private AgingState state;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        utility = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
        state = new AgingState(null, context, utility, "13-11-2020", output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Persist", state.getNextStateName());
        assertNotEquals("Aging", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        //TODO when aging process done.
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Aging", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertNotEquals("NextSeason", state.getNextStateName());
        assertEquals("Persist", state.getNextStateName());

        utility.setSeasonOverStatus(true);
        state.nextState(context);
        assertNotEquals("Persist", state.getNextStateName());
        assertEquals("NextSeason", state.getNextStateName());
    }
}