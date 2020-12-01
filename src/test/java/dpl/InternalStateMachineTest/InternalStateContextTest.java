package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.SimulationManagement.InternalStateMachine.ISimulationState;
import dpl.SimulationManagement.InternalStateMachine.InternalEndState;
import dpl.SimulationManagement.InternalStateMachine.InternalStartState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

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
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        standingMock = StandingsMockDb.getInstance();
        state = (InternalStartState)SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStartState(input, output, "", null, context, null, standingMock);
        context.setState(state);
    }

    @Test
    public void nextStateTest() {
        context.nextState();
        assertEquals("InternalSimulation", context.currentStateName);
        assertNotEquals("Negative", context.currentStateName);
        context.setState(state);
    }

    @Test
    public void setStateTest() {
        context.setState(state);
        assertEquals("Start", context.currentStateName);
        assertNotEquals("Negative", context.currentStateName);
    }

    @Test
    public void getCurrentStateTest() {
        assertTrue(context.getCurrentstate() instanceof InternalStartState);
        assertFalse(context.getCurrentstate() instanceof InternalEndState);
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

    @Test
    public void shouldContinueTest() {
        assertTrue(context.shouldContinue());
        assertFalse(!context.shouldContinue());
    }

}