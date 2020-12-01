package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.SimulationManagement.InternalStateMachine.InternalStartState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class InternalStartStateTest {

    private static InternalStartState state;
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
        state = (InternalStartState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStartState(input, output, "", null, context, null, standingMock);
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("InternalSimulation", context.currentStateName);
        state.nextState(context);
        assertEquals("InternalSimulation", context.currentStateName);
    }

    @Test
    public void getStateNameTest() {
        assertNotEquals("Negative", state.getStateName());
        assertEquals("Start", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("InternalSimulation", state.getNextStateName());
        state.nextState(context);
        assertEquals("InternalSimulation", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String input = "0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        state.doProcessing();
        assertNotEquals("1", String.valueOf(state.numOfSeasons));
        assertEquals("0", String.valueOf(state.numOfSeasons));
    }

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }
}