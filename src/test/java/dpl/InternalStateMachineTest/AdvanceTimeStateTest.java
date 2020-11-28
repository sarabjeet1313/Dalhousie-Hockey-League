package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.AdvanceTimeState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
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
        input = new CmdUserInput();
        output = new CmdUserOutput();
        utility = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
        state = new AdvanceTimeState(null, null, utility, null, null, "02-04-2021", "03-04-2021", output, context, 0);
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