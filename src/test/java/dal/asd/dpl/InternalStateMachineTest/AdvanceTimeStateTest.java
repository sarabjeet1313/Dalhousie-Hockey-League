package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class AdvanceTimeStateTest {
    private AdvanceTimeState state;
    private IUserInput input;
    private IUserOutput output;
    private InternalStateContext context;
    private Calendar seasonCalendar;
    private ScheduleUtlity utility;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        seasonCalendar = Calendar.getInstance();
        utility = new ScheduleUtlity(0);
        context = new InternalStateContext(input, output);
        //TODO to be replaced with mock schedule
        ISchedule schedule = new RegularSeasonScheduleState(seasonCalendar, output);
        state = new AdvanceTimeState(schedule,null, "02-04-2021", "03-04-2021", utility, output, context);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Training", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertEquals("03-04-2021", state.getCurrentDate());
    }

    @Test
    public void getStateNameTest() {
        assertEquals("AdvanceTime", state.getStateName());
    }

    @Test
    public void getCurrentDateTest() {
        assertEquals("02-04-2021", state.getCurrentDate());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertEquals("Training", state.getNextStateName());
    }
}