package dpl.SimulationStateMachineTest;

import dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.SimulationStateMachine.CreateTeamState;
import dpl.SimulationStateMachine.StateContext;
import dpl.Standings.IStandingsPersistance;
import dpl.StandingsTest.StandingsMockDb;
import dpl.TeamManagement.Coach;
import dpl.TeamManagement.ICoachPersistance;
import dpl.TeamManagement.IManagerPersistance;
import dpl.TeamManagement.League;
import dpl.TeamManagement.Manager;
import dpl.TeamManagement.Player;
import dpl.TeamManagementTest.CoachMockData;
import dpl.TeamManagementTest.GamaplayConfigMockData;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.TeamManagementTest.LeagueObjectTestData;
import dpl.TeamManagementTest.ManagerMockData;
import dpl.Trading.ITradePersistence;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

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