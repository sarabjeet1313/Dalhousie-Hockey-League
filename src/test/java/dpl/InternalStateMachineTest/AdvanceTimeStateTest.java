package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.SimulationManagement.InternalStateMachine.AdvanceTimeState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdvanceTimeStateTest {
    private AdvanceTimeState state;
    private IUserInput input;
    private IUserOutput output;
    private InternalStateContext context;
    private SeasonCalendar utility;

    @Before
    public void setUp() throws Exception {
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        state = (AdvanceTimeState)SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().AdvanceTimeState(null, null, utility, null, null, "02-04-2021", "03-04-2021", output, context, 0);
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
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
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
        assertNotEquals("03-04-2021", state.getCurrentDate());
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