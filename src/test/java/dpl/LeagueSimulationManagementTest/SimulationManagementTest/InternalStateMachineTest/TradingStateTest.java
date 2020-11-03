package dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachineTest;

import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.*;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.ISchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.RegularSeasonSchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Trading.Trade;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TradingTest.TradeObjectTestMockData;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class TradingStateTest {

    private TradingState state;
    private League leagueToSimulate;
    private LeagueMockData mockLeague;
    private ISchedule schedule;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private IUserOutput output;
    private IUserInput input;
    private Trade trade;
    private Calendar calendar;
    private TradeObjectTestMockData tradeMock;

    @Before
    public void setUp() throws Exception {
        mockLeague = new LeagueMockData();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueToSimulate = mockLeague.getTestData();
        calendar = Calendar.getInstance();
        schedule = new RegularSeasonSchedule(calendar, output);
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        tradeMock = new TradeObjectTestMockData();
        trade = new Trade(tradeMock);
        state = new TradingState(leagueToSimulate, trade, context, output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Aging", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getUpdatedLeagueTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Trading", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("Aging", state.getNextStateName());
        state.nextState(context);
        assertEquals("Aging", state.getNextStateName());
    }
}