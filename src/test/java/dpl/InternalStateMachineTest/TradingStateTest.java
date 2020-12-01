package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.TradingState;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.TradingTest.TradeObjectTestMockData;
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
        mockLeague = LeagueMockData.getInstance();
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        leagueToSimulate = mockLeague.getTestData();
        calendar = Calendar.getInstance();
        schedule = SystemConfig.getSingleInstance().getScheduleAbstractFactory().RegularSeasonSchedule(calendar, output);
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        tradeMock = TradeObjectTestMockData.getInstance();
        trade = SystemConfig.getSingleInstance().getTradingAbstractFactory().Trade(tradeMock);
        state = (TradingState)SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().TradingState(leagueToSimulate, trade, context, output, null, "", "", 0, null, null, schedule);
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

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }
}