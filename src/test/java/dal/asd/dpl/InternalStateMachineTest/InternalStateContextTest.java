package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.ISimulationState;
import dal.asd.dpl.InternalStateMachine.InternalEndState;
import dal.asd.dpl.InternalStateMachine.InternalStartState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class InternalStateContextTest {

    private static ISimulationState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static InternalStateContext context;

    @BeforeAll
    public static void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        state = new InternalStartState(input, output, "");
        context = new InternalStateContext(input, output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Simulate", context.currentStateName);
    }

    @Test
    public void setStateTest() {
        context.setState(state);
        assertEquals("Start", context.currentStateName);
    }

    @Test
    public void doProcessing() {
        context.setState(new InternalEndState(input, output));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        context.doProcessing();

        String expected  = "Thanks for using the Dynasty mode. Please come back soon.\n";
        assertEquals(expected, out.toString());
    }
}