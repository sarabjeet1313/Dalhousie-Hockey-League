package dal.asd.dpl.SimulationStateMachineTest;
import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.SimulationStateMachine.LoadTeamState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.StandingsTest.StandingsMockDb;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

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