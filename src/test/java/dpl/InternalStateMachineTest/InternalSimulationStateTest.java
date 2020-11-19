package dpl.InternalStateMachineTest;

import dpl.ErrorHandling.RetirementManagementException;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalSimulationState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.StandingsTest.StandingsMockDb;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.TradingTest.TradeObjectTestMockData;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }

}