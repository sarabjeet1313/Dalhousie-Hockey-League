package dal.asd.dpl.InternalStateMachineTest;
import dal.asd.dpl.InternalStateMachine.InternalEndState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class InternalEndStateTest {
    private static InternalEndState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static InternalStateContext context;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        state = new InternalEndState(input, output);
        context = new InternalStateContext(input, output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("None", state.getNextStateName());
    }

    @Test
    public void getStateNameTest() {
        assertEquals("End", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertEquals("None", state.getNextStateName());
    }

    @Test
    public void doProcessingTest(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        state.doProcessing();

        String expected  = "Thanks for using the Dynasty mode. Please come back soon.";
        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertEquals(expected, gotOutput);
    }
}