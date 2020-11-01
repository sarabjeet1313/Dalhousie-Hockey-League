package dal.asd.dpl.SimulationStateMachineTest;
import dal.asd.dpl.Database.GameConfigDB;
import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.SimulationStateMachine.CreateTeamState;
import dal.asd.dpl.SimulationStateMachine.StateContext;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.ICoachPersistance;
import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.TeamManagementTest.CoachMockData;
import dal.asd.dpl.TeamManagementTest.GamaplayConfigMockData;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.TeamManagementTest.LeagueObjectTestData;
import dal.asd.dpl.TeamManagementTest.ManagerMockData;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Manager;
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

    private CreateTeamState state;
    private IUserInput input;
    private IUserOutput output;
    private StateContext context;
    private LeagueMockData mockData;
    private LeagueObjectTestData data = new LeagueObjectTestData();
	private ICoachPersistance coachMock = new CoachMockData();
	private IGameplayConfigPersistance configMock = new GamaplayConfigMockData();
	private IManagerPersistance managerMock = new ManagerMockData();
	

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        mockData = new LeagueMockData();
        state = new CreateTeamState(input, output, data.getLeagueData(), mockData, coachMock, configMock, managerMock);
        context = new StateContext(input, output);
        context.setState(state);
    }
    
    //To test input for create team
    @Test
    public void doProcessingTest() {
 	//context.doProcessing();
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
    	Coach headCoach = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4, coachMock);
    	League league = new LeagueObjectTestData().getLeagueData();
    	Manager manager1 = new Manager("Karen Potam", managerMock);
    	List<Player> pList = league.getFreeAgents();
        boolean success = state.createTeamInLeague("Eastern Conference", "Atlantic", "testTeam", manager1, headCoach, pList, league);
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