package dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachineTest;

import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.InternalSimulationState;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Trading.ITradePersistence;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TradingTest.TradeObjectTestMockData;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

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
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueMock = new LeagueMockData();
        utlity = new SeasonCalendar(0, output);
        context = new InternalStateContext(input, output);
        standingMock = new StandingsMockDb(0);
        tradeMock = new TradeObjectTestMockData();
        state = new InternalSimulationState(input, output, 1, "testTeam", leagueMock.getTestData(), context, tradeMock, standingMock);
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

}