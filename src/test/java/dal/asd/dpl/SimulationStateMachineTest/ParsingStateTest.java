package dal.asd.dpl.SimulationStateMachineTest;
import dal.asd.dpl.Database.GameConfigDB;
import dal.asd.dpl.SimulationStateMachine.ParsingState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.StandingsTest.StandingsMockDb;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.CoachMockData;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.TeamManagementTest.ManagerMockData;
import dal.asd.dpl.Trading.ITradePersistance;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import static org.junit.Assert.*;

public class ParsingStateTest {
	
    private ParsingState state;
    private IUserInput input;
    private IUserOutput output;
    private StateContext context;
    private LeagueMockData leagueDb;
    private String filePath;
    private CoachMockData coachMock;
    private GameConfigDB configMock;
    private ManagerMockData managerMock;
    private IStandingsPersistance standingMock;
    private ITradePersistance tradeMock;

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