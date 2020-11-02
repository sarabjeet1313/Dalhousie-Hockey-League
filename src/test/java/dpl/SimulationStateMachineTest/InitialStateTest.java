package dpl.SimulationStateMachineTest;
import org.junit.Before;
import org.junit.Test;

import dpl.SimulationStateMachine.InitialState;
import dpl.SimulationStateMachine.StateContext;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        String expected  = "Welcome to the Dynasty Mode. It's time to conquer the hockey arena.";
        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertEquals(expected, gotOutput);
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