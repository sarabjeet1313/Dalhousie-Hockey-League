package dal.asd.dpl.InternalStateMachineTest;
import dal.asd.dpl.InternalStateMachine.AdvanceToNextSeasonState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.InternalStateMachine.ScheduleUtlity;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.*;

public class AdvanceToNextSeasonStateTest {

    private IUserInput input;
    private IUserOutput output;
    private ScheduleUtlity utility;
    private InternalStateContext context;
    private AdvanceToNextSeasonState state;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        utility = new ScheduleUtlity(0);
        context = new InternalStateContext(input, output);
        state = new AdvanceToNextSeasonState(null, null, context, utility, "13-11-2020", output);
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("Persist", state.getNextStateName());
        state.nextState(context);
        assertEquals("Persist", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        // TODO after getting the logic for processing.
    }

    @Test
    public void getStateNameTest() {
        assertEquals("NextSeason", state.getStateName());
        assertNotEquals("Training", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("Persist", state.getNextStateName());
        state.nextState(context);
        assertEquals("Persist", state.getNextStateName());
    }
}