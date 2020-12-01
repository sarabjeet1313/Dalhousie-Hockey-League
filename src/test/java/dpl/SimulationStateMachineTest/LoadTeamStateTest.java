package dpl.SimulationStateMachineTest;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.SimulationManagement.SimulationStateMachine.LoadTeamState;
import dpl.SimulationManagement.SimulationStateMachine.StateContext;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.UserInputOutput.UserInput.CmdUserInput;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

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