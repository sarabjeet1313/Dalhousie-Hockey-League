package dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachineTest;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.InternalStartState;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

import org.junit.Test;
import org.junit.Before;
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
        input = new CmdUserInput();
        output = new CmdUserOutput();
        context = new InternalStateContext(input, output);
        standingMock = new StandingsMockDb(0);
        state = new InternalStartState(input, output, "", null, context, null, standingMock);
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
        this.input.setInput();
        assertNotEquals("1", String.valueOf(state.numOfSeasons));
        assertEquals("0", String.valueOf(state.numOfSeasons));
    }

}