package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.SimulationManagement.InternalStateMachine.InternalSimulationState;
import dpl.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueManagementTest.TradingTest.TradeObjectTestMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InternalSimulationStateTest {
    private InternalSimulationState state;
    private IUserInput input;
    private IUserOutput output;
    private InternalStateContext context;
    private LeagueMockData leagueMock;
    private SeasonCalendar utlity;
    private IStandingsPersistance standingMock;
    private ITradePersistence tradeMock;

    @Before
    public void setUp() throws Exception {
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        leagueMock = LeagueMockData.getInstance();
        utlity = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        standingMock = StandingsMockDb.getInstance();
        tradeMock = TradeObjectTestMockData.getInstance();
        state = (InternalSimulationState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalSimulationState(input, output, 1, "testTeam", leagueMock.getTestData(), context, tradeMock, standingMock);
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("End", context.currentStateName);
        context.setState(state);
        context.nextState();
        assertEquals("End", context.currentStateName);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("InternalSimulation", state.getStateName());
        assertNotEquals("End", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("InternalEndState", state.getNextStateName());
        state.nextState(context);
        assertEquals("InternalEndState", state.getNextStateName());
    }

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }

}