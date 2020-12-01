package dpl.InternalStateMachineTest;

import dpl.SimulationManagement.InternalStateMachine.InternalEndState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
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
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        state = (InternalEndState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalEndState(output);
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("None", state.getNextStateName());
        context.setState(state);
        context.nextState();
        assertEquals("None", state.getNextStateName());
    }

    @Test
    public void getStateNameTest() {
        assertEquals("End", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        context.setState(state);
        context.nextState();
        assertEquals("None", state.getNextStateName());
    }

    @Test
    public void doProcessingTest(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        state.doProcessing();
        String expected  = "Thanks for using the DHL's Dynasty mode. Please come back soon.";
        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertNotEquals("Negative", gotOutput);
        assertEquals(expected, gotOutput);
    }

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }
}