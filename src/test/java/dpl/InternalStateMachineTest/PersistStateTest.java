package dpl.InternalStateMachineTest;

import dpl.InternalStateMachine.*;
import dpl.Schedule.ISchedule;
import dpl.Schedule.RegularSeasonSchedule;
import dpl.Schedule.SeasonCalendar;
import dpl.Standings.IStandingsPersistance;
import dpl.Standings.StandingInfo;
import dpl.StandingsTest.StandingsMockDb;
import dpl.TeamManagement.League;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

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