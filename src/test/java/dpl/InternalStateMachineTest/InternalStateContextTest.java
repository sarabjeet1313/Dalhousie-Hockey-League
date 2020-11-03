package dpl.InternalStateMachineTest;
import dpl.InternalStateMachine.ISimulationState;
import dpl.InternalStateMachine.InternalEndState;
import dpl.InternalStateMachine.InternalStartState;
import dpl.InternalStateMachine.InternalStateContext;
import dpl.Standings.IStandingsPersistance;
import dpl.StandingsTest.StandingsMockDb;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

import org.junit.Test;
import org.junit.Before;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class InternalStateContextTest {

    private static ISimulationState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static InternalStateContext context;
    private IStandingsPersistance standingMock;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        context = new InternalStateContext(input, output);
        standingMock = new StandingsMockDb(0);
        state = new InternalStartState(input, output, "", null, context, null, standingMock);
        context.setState(state);
    }

    @Test
    public void nextStateTest() {
        context.nextState();
        assertEquals("InternalSimulation", context.currentStateName);
        context.setState(state);
    }

    @Test
    public void setStateTest() {
        context.setState(state);
        assertEquals("Start", context.currentStateName);
    }

    @Test
    public void doProcessingTest() {
        context.setState(new InternalEndState(output));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        context.doProcessing();
        String expected  = "Thanks for using the DHL's Dynasty mode. Please come back soon.";
        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertEquals(expected, gotOutput);
    }
}