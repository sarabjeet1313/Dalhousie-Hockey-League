package dal.asd.dpl.InternalStateMachineTest;
import dal.asd.dpl.InternalStateMachine.InternalSimulationState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
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
    private static InternalSimulationState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static InternalStateContext context;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        state = new InternalSimulationState(input, output,1,"testTeam");
        context = new InternalStateContext(input, output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("GenerateRegularSeasonSchedule", context.currentStateName);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Simulate", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertEquals("GenerateRegularSeasonSchedule", state.getNextStateName());
    }

    @Test
    public void doProcessing() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String expected = "Season 1 simulated for testTeam ...";

        state.doProcessing();

        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertEquals(expected, gotOutput);
    }
}