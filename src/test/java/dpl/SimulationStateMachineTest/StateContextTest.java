package dpl.SimulationStateMachineTest;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine.IState;
import dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine.InitialState;
import dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine.LoadTeamState;
import dpl.LeagueSimulationManagement.SimulationManagement.SimulationStateMachine.StateContext;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class StateContextTest {
    private static IState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static StateContext context;
    private static ILeaguePersistance mockLeague;
    private IGameplayConfigPersistance configMock;
    private IStandingsPersistance standingMock;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        mockLeague = new LeagueMockData();
        standingMock = new StandingsMockDb(0);
        state = new InitialState(input, output);
        context = new StateContext(input, output);
        context.setState(state);
    }

    @Test
    public void nextStateTest() {
        context.setState(new LoadTeamState(input, output, mockLeague, configMock, null, standingMock));
        context.nextState();
        assertEquals("Simulate", context.currentStateName);
        context.setState(state);
    }

    @Test
    public void setStateTest() {
        context.setState(state);
        assertEquals("Initial", context.currentStateName);
    }

    @Test
    public void doProcessingTest() {
        context.setState(state);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        context.doProcessing();
        String expected  = "Welcome to the Dynasty Mode. It's time to conquer the hockey arena.";
        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertEquals(expected, gotOutput);
    }
}