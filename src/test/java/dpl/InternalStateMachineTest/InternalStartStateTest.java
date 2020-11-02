package dpl.InternalStateMachineTest;
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
        state.nextState(context);
        assertEquals("InternalSimulation", context.currentStateName);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Start", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
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
        assertEquals("0", String.valueOf(state.numOfSeasons));
    }

}