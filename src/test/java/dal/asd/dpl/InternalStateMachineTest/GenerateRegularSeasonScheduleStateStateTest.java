package dal.asd.dpl.InternalStateMachineTest;
import dal.asd.dpl.InternalStateMachine.GenerateRegularSeasonScheduleState;
import dal.asd.dpl.InternalStateMachine.InternalStateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenerateRegularSeasonScheduleStateStateTest {
    private static GenerateRegularSeasonScheduleState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static InternalStateContext context;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        context = new InternalStateContext(input, output);
        state = new GenerateRegularSeasonScheduleState(null, input, output, 0, context);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("AdvanceTime", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        // TODO - once we finalize the scheduling algo
    }

    @Test
    public void getRegularSeasonEndDateTest() {
        assertEquals("03-04-2021", state.getRegularSeasonEndDate());
    }

    @Test
    public void getStateNameTest() {
        assertEquals("GenerateRegularSeasonSchedule", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertEquals("AdvanceTime", state.getNextStateName());
    }
}