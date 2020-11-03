package dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachineTest;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine.IState;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine.InitialState;
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