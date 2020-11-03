package dpl.InternalStateMachineTest;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.ISimulationState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalEndState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStartState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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