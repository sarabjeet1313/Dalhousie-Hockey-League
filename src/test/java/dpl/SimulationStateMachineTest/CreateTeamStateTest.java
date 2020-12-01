package dpl.SimulationStateMachineTest;

import dpl.SystemConfig;
import dpl.LeagueManagementTest.GameplayConfigurationTest.GameplayConfigMockData;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.SimulationManagement.SimulationStateMachine.CreateTeamState;
import dpl.SimulationManagement.SimulationStateMachine.StateContext;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagementTest.TeamManagementTest.CoachMockData;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueObjectTestData;
import dpl.LeagueManagementTest.TeamManagementTest.ManagerMockData;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.UserInputOutput.UserInput.CmdUserInput;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

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
	private LeagueObjectTestData data;
	private ICoachPersistance coachMock;
	private IGameplayConfigPersistance configMock;
	private IManagerPersistance managerMock;
	private IStandingsPersistance standingMock;
	private ITradePersistence tradeMock;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();

	@Before
	public void setUp() throws Exception {
		input = new CmdUserInput();
		output = new CmdUserOutput();
		mockData = new LeagueMockData();
		data = new LeagueObjectTestData();
		coachMock = new CoachMockData();
		configMock = new GameplayConfigMockData();
		managerMock = new ManagerMockData();
		standingMock = new StandingsMockDb(0);
		state = new CreateTeamState(input, output, data.getLeagueData(), mockData, coachMock, configMock, managerMock,
				tradeMock, standingMock);
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
	public void createTeamInLeagueTest() {
		Coach headCoach = teamManagement.CoachWithDbParameters("Mary Smith", 0.2, 0.3, 0.1, 0.4, coachMock);
		League league = new LeagueObjectTestData().getLeagueData();
		Manager manager1 = teamManagement.ManagerWithDbParameters("Karen Potam", "normal" ,managerMock);
		List<Player> pList = league.getFreeAgents();
		boolean success = state.createTeamInLeague("Eastern Conference", "Atlantic", "testTeam", manager1, headCoach,
				pList, league);
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