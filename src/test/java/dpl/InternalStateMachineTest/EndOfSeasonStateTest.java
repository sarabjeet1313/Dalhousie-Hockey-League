package dpl.InternalStateMachineTest;

import dpl.SimulationManagement.InternalStateMachine.EndOfSeasonState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class EndOfSeasonStateTest {

    private EndOfSeasonState state;
    private IUserInput input;
    private IUserOutput output;
    private InternalStateContext context;

    @Before
    public void setUp() throws Exception {
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        state = (EndOfSeasonState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().EndOfSeasonState(output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("None", state.getNextStateName());
        assertNotEquals("Aging", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        state.doProcessing();
        String expected  = "Season has been simulated successfully.";
        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertEquals(expected, gotOutput);
    }

    @Test
    public void shouldContinueTest() {
        assertFalse(state.shouldContinue());
        assertTrue(!state.shouldContinue());
    }

    @Test
    public void getStateNameTest() {
        assertEquals("SeasonEndState", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertNotEquals("Negative", state.getNextStateName());
        assertEquals("None", state.getNextStateName());
    }
}