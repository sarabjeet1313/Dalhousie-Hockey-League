package dal.asd.dpl.SimulationStateMachineTest;

import dal.asd.dpl.InternalStateMachine.ISimulationState;
import dal.asd.dpl.InternalStateMachine.InternalEndState;
import dal.asd.dpl.InternalStateMachine.InternalStartState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.SimulationStateMachine.IState;
import dal.asd.dpl.SimulationStateMachine.InitialState;
import dal.asd.dpl.SimulationStateMachine.LoadTeamState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.teammanagement.ILeague;
import dal.asd.dpl.teammanagement.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.junit.Assert.*;

public class StateContextTest {

    private static IState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static StateContext context;
    private static ILeague mockLeague;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        mockLeague = new LeagueMockData();
        state = new InitialState(input, output);
        context = new StateContext(input, output);
        context.setState(state);
    }

    @Test
    public void nextStateTest() {
        context.setState(new LoadTeamState(input, output, mockLeague));
        context.nextState();
        assertEquals("Simulate", context.currentStateName);
        context.setState(state);
    }

    @Test
    public void setStateTest() {
        context.setState(state);
        assertEquals("Initial", context.currentStateName);
    }

    @Test
    public void doProcessingTest() {
        context.setState(state);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        context.doProcessing();
        String expected  = "Welcome to the Dynasty Mode. It's time to conquer the hockey arena.";

        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertEquals(expected, gotOutput);
    }
}