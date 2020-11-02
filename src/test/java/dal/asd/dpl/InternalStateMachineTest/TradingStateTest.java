package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.RegularSeasonSchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.Trading.Trade;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
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
        trade = new Trade();
        state = new TradingState(leagueToSimulate, trade, context, output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Aging", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    //TODO TEST - need to pass trade mock object - In @Before it shouls be trade = new Trade(tradeMockobj)
//    @Test
//    public void doProcessingTest() {
//        state.doProcessing();
//        assertTrue(null == state.getUpdatedLeague());
//        assertTrue( state.getUpdatedLeague() instanceof League);
//    }
//
//    //TODO TEST
//    @Test
//    public void getUpdatedLeagueTest() {
//        state.doProcessing();
//        assertFalse(null == state.getUpdatedLeague());
//        assertTrue( state.getUpdatedLeague() instanceof League);
//    }

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