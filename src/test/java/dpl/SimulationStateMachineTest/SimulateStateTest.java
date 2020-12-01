package dpl.SimulationStateMachineTest;
import dpl.SimulationManagement.SimulationStateMachine.SimulateState;
import dpl.SimulationManagement.SimulationStateMachine.StateContext;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.UserInputOutput.UserInput.CmdUserInput;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimulateStateTest {
    private static SimulateState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static StateContext context;
    private IStandingsPersistance standingMock;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        standingMock = new StandingsMockDb(0);
        state = new SimulateState(input, output, "", null, null, standingMock);
        context = new StateContext(input, output);
        context.setState(state);
    }

    @Test
    public void nextStateTest() {
        context.nextState();
        assertEquals("None", state.getNextStateName());
        context.setState(state);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Simulate", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        context.nextState();
        assertEquals("None", state.getNextStateName());
        context.setState(state);
    }
}