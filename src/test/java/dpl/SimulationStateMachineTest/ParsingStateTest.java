package dpl.SimulationStateMachineTest;

import dpl.LeagueManagementTest.GameplayConfigurationTest.GameplayConfigMockData;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.SimulationManagement.SimulationStateMachine.ParsingState;
import dpl.SimulationManagement.SimulationStateMachine.StateContext;
import dpl.UserInputOutput.UserInput.CmdUserInput;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.LeagueManagementTest.TeamManagementTest.CoachMockData;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueManagementTest.TeamManagementTest.ManagerMockData;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ParsingStateTest {
	
    private ParsingState state;
    private IUserInput input;
    private IUserOutput output;
    private StateContext context;
    private LeagueMockData leagueDb;
    private String filePath;
    private CoachMockData coachMock;
    private GameplayConfigMockData configMock;
    private ManagerMockData managerMock;
    private IStandingsPersistance standingMock;
    private ITradePersistence tradeMock;

    @Before
    public void setUp() throws Exception {
        URL i = getClass().getClassLoader().getResource("input.json");
        filePath = i.getPath();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueDb = new LeagueMockData();
        standingMock = new StandingsMockDb(0);
        state = new ParsingState(input, output, filePath, leagueDb, coachMock, configMock, managerMock, tradeMock, standingMock);
        context = new StateContext(input, output);
        context.setState(state);
    }

    @Test
    public void nextStateTest() {
        context.nextState();
        assertEquals("Create Team", state.getNextStateName());
        context.setState(state);
    }

    @Test
    public void doProcessingTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        context.doProcessing();
        League initializedLeague = leagueDb.getTestData();
        String expected  = "Welcome to the Parsing State. It's time to parse the JSON and initialize your league.";
        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertEquals(expected, gotOutput);
        assertNotNull(initializedLeague);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Parsing", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        context.nextState();
        assertEquals("Create Team", state.getNextStateName());
        context.setState(state);
    }
    
}