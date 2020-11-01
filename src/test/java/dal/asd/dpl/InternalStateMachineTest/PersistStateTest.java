package dal.asd.dpl.InternalStateMachineTest;

import dal.asd.dpl.InternalStateMachine.*;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.RegularSeasonSchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.Standings.IStandingsPersistance;
import dal.asd.dpl.Standings.StandingInfo;
import dal.asd.dpl.StandingsTest.StandingsMockDb;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class PersistStateTest {

    private PersistState state;
    private League leagueToSimulate;
    private IUserInput input;
    private IUserOutput output;
    private ISchedule schedule;
    private Calendar calendar;
    private StandingInfo standings;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private IStandingsPersistance standingsDb;

    @Before
    public void setUp() throws Exception {
        leagueToSimulate = new LeagueMockData().getTestData();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        calendar = Calendar.getInstance();
        standingsDb = new StandingsMockDb(0);
        schedule = new RegularSeasonSchedule(calendar, output);
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb);
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        state = new PersistState(leagueToSimulate, schedule, standings, context, utility, "13-11-2020", output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("AdvanceTime", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        // TODO pending for logic
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Persist", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("AdvanceTime", state.getNextStateName());
        state.nextState(context);
        assertEquals("AdvanceTime", state.getNextStateName());
    }
}