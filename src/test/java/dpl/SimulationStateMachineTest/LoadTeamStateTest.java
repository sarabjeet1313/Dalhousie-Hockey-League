package dpl.SimulationStateMachineTest;
import dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.SimulationStateMachine.LoadTeamState;
import dpl.SimulationStateMachine.StateContext;
import dpl.Standings.IStandingsPersistance;
import dpl.StandingsTest.StandingsMockDb;
import dpl.TeamManagement.ILeaguePersistance;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoadTeamStateTest {
    private LoadTeamState state;
    private IUserInput input;
    private IUserOutput output;
    private StateContext context;
    private ILeaguePersistance leagueMock;
    private IGameplayConfigPersistance configMock;
    private IStandingsPersistance standingMock;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueMock = new LeagueMockData();
        standingMock = new StandingsMockDb(0);
        state = new LoadTeamState(input, output, leagueMock, configMock, null, standingMock);
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
    public void getStateNameTest() {
        assertEquals("Load Team", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        context.nextState();
        assertEquals("Simulate", state.getNextStateName());
        context.setState(state);
    }
}