package dal.asd.dpl.SimulationStateMachineTest;

import dal.asd.dpl.SimulationStateMachine.CreateTeamState;
import dal.asd.dpl.SimulationStateMachine.InitialState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.teammanagement.ILeague;
import dal.asd.dpl.teammanagement.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class CreateTeamStateTest {

    private static CreateTeamState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static StateContext context;
    private static LeagueMockData mockData;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        mockData = new LeagueMockData();
        state = new CreateTeamState(input, output, mockData.getTestData(), mockData);
        context = new StateContext(input, output);
        context.setState(state);
    }

    @Test
    public void nextStateTest() {
        context.nextState();
        assertEquals("Simulate", state.getNextStateName());
        context.setState(state);
    }

    @Test
    public void createTeamInLeagueTest() {
        boolean success = state.createTeamInLeague("Eastern Conference", "Atlantic", "testTeam", "testGM", "testHC", mockData.getTestData());
        assertTrue(success);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Create Team", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        context.nextState();
        assertEquals("Simulate", state.getNextStateName());
        context.setState(state);
    }
}