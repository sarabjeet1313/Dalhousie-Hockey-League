package dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachineTest;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine.CreateTeamState;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.SimulationStateMachine.StateContext;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Coach;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Manager;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Player;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.CoachMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.GamaplayConfigMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueObjectTestData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.ManagerMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Trading.ITradePersistence;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

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

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        mockData = new LeagueMockData();
        data = new LeagueObjectTestData();
        coachMock = new CoachMockData();
        configMock = new GamaplayConfigMockData();
        managerMock = new ManagerMockData();
        standingMock = new StandingsMockDb(0);
        state = new CreateTeamState(input, output, data.getLeagueData(), mockData, coachMock, configMock, managerMock, tradeMock, standingMock);
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