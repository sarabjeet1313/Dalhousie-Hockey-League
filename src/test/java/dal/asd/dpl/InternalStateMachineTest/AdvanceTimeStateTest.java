package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
import dal.asd.dpl.TeamManagement.Leagues;
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
        state = new AdvanceTimeState(null,null, "02-04-2021", "03-04-2021", utility, output, context);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Training", state.getNextStateName());
        assertNotEquals("Generate Playoff", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertEquals("03-04-2021", state.getCurrentDate());
        assertNotEquals("02-04-2021", state.getCurrentDate());
    }

    @Test
    public void incrementCurrentDayTest() {
        state.incrementCurrentDay();
        assertEquals("03-04-2021", state.getCurrentDate());
        assertNotEquals("02-04-2021", state.getCurrentDate());
    }

    @Test
    public void isALastDayTest() {
        state.incrementCurrentDay();
        assertTrue(state.isALastDay());

        state.setCurrentDate("01-02-2017");
        state.incrementCurrentDay();
        assertFalse(state.isALastDay());
    }

    @Test
    public void setCurrentDateTest() {
        state.setCurrentDate("01-02-2027");
        assertEquals("01-02-2027", state.getCurrentDate());
        assertNotEquals("03-02-2098", state.getCurrentDate());
    }

    @Test
    public void getCurrentDateTest() {
        assertEquals("02-04-2021", state.getCurrentDate());
    }

    @Test
    public void getStateNameTest() {
        assertEquals("AdvanceTime", state.getStateName());
        assertNotEquals("Training", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        state.nextState(context);
        assertEquals("Training", state.getNextStateName());
        assertNotEquals("AdvanceTime", state.getNextStateName());
    }
}