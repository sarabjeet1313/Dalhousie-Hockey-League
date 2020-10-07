package dal.asd.dpl.SimulationStateMachineTest;

import dal.asd.dpl.InitializeModels.InitializeLeagues;
import dal.asd.dpl.SimulationStateMachine.InitialState;
import dal.asd.dpl.SimulationStateMachine.ParsingState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.teammanagement.ILeague;
import dal.asd.dpl.teammanagement.LeagueMockData;
import dal.asd.dpl.teammanagement.Leagues;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;


public class ParsingStateTest {

    private static ParsingState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static StateContext context;
    private static LeagueMockData leagueDb;
    private static String filePath;

    @BeforeAll
    public static void setUp() throws Exception {

        File file = new File("resources/json/input.json");
        filePath = file.getCanonicalPath();


        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueDb = new LeagueMockData();
     //   filePath = "/Users/sarabjeetsingh/AdvSDC/input.json";
        state = new ParsingState(input, output, filePath, leagueDb);
        context = new StateContext(input, output);
        context.setState(state);
    }

    @Test
    public void nextState() {
        context.nextState();
        assertEquals("Create Team", state.getNextStateName());
        context.setState(state);
    }

    @Test
    public void doProcessing() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        context.doProcessing();

        Leagues initializedLeague = leagueDb.getTestData();

        String expected  = "Welcome to the Parsing State. It's time to parse the JSON and initialize your league.\n";
        assertEquals(expected, out.toString());
        assertNotNull(initializedLeague);
    }

    @Test
    public void getStateName() {
        assertEquals("Parsing", state.getStateName());
    }

    @Test
    public void getNextStateName() {
        context.nextState();
        assertEquals("Create Team", state.getNextStateName());
        context.setState(state);
    }
}