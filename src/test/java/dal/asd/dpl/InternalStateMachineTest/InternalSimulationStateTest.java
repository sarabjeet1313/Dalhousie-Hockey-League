package dal.asd.dpl.InternalStateMachineTest;
import dal.asd.dpl.InternalStateMachine.InternalSimulationState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.InternalStateMachine.ScheduleUtlity;
import dal.asd.dpl.TeamManagement.LeagueMockData;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class InternalSimulationStateTest {
    private InternalSimulationState state;
    private IUserInput input;
    private IUserOutput output;
    private InternalStateContext context;
    private LeagueMockData leagueMock;
    private ScheduleUtlity utlity;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueMock = new LeagueMockData();
        utlity = new ScheduleUtlity(1);
        context = new InternalStateContext(input, output);
        state = new InternalSimulationState(input, output,1,"testTeam", leagueMock.getTestData(), context);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("End", context.currentStateName);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Simulate", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertEquals("InternalEndState", state.getNextStateName());
    }

    @Test
    public void doProcessing() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String expected = "Season 1 winner is : Boston";

        state.doProcessing();

        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");


        assertEquals(expected, gotOutput.substring(gotOutput.length()-27, gotOutput.length()));
    }
}