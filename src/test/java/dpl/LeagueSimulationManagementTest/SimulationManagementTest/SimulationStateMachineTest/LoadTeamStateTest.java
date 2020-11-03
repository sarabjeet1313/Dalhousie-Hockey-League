package dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachineTest;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine.LoadTeamState;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine.StateContext;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

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