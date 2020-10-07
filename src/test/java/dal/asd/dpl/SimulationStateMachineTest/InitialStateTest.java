package dal.asd.dpl.SimulationStateMachineTest;

import dal.asd.dpl.InternalStateMachine.InternalStartState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.SimulationStateMachine.InitialState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class InitialStateTest {

    private static InitialState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static StateContext context;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        state = new InitialState(input, output);
        context = new StateContext(input, output);
        context.setState(state);
    }

    @Test
    public void nextStateTest() {
        context.nextState();
        assertEquals("None", state.getNextStateName());
        context.setState(state);
    }

    @Test
    public void doProcessingTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        context.doProcessing();

        String expected  = "Welcome to the Dynasty Mode. It's time to conquer the hockey arena.\n";
        assertEquals(expected, out.toString());
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Initial", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        context.nextState();
        assertEquals("None", state.getNextStateName());
        context.setState(state);
    }
}