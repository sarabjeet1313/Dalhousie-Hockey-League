package dal.asd.dpl.SimulationStateMachineTest;
import dal.asd.dpl.SimulationStateMachine.CreateTeamState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.TeamManagementTest.LeagueObjectTestData;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class CreateTeamStateTest {

    private static CreateTeamState state;
    private static IUserInput input;
    private static IUserOutput output;
    private static StateContext context;
    private static LeagueMockData mockData;
    private LeagueObjectTestData data = new LeagueObjectTestData();

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        mockData = new LeagueMockData();
        
        state = new CreateTeamState(input, output, data.getLeagueData(), mockData);
        context = new StateContext(input, output);
        context.setState(state);
    }
    
    //To test input for create team
    @Test
    public void doProcessingTest() {
 //	context.doProcessing();
    	Coach headCoach = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4);
    	LeagueObjectTestData data = new LeagueObjectTestData();
    	League league = data.getLeagueData();
    	List<Player> pList = league.getFreeAgents();
        boolean success = state.createTeamInLeague("Eastern Conference", "Atlantic", "testTeam", "testGM", headCoach, pList, mockData.getTestData());
        assertTrue(success);
    }

    @Test
    public void nextStateTest() {
        context.nextState();
        assertEquals("Simulate", state.getNextStateName());
        context.setState(state);
    }

    @Test
    public void createTeamInLeagueTest() {
    	Coach headCoach = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4);
    	LeagueObjectTestData data = new LeagueObjectTestData();
    	League league = data.getLeagueData();
    	List<Player> pList = league.getFreeAgents();
        boolean success = state.createTeamInLeague("Eastern Conference", "Atlantic", "testTeam", "testGM", headCoach, pList, league);
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