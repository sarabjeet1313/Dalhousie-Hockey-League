package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.*;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.RegularSeasonSchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.ScheduleTest.MockSchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class SimulateGameStateTest {

    private SimulateGameState state;
    private League leagueToSimulate;
    private ISchedule schedule;
    private MockSchedule mockSchedule;
    private StandingInfo standings;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private IUserInput input;
    private IUserOutput output;
    private Calendar calendar;
    private IStandingsPersistance standingsDb;

    @Before
    public void setUp() throws Exception {
        leagueToSimulate = new LeagueMockData().getTestData();
        calendar = Calendar.getInstance();
        schedule = new RegularSeasonSchedule(calendar, output);
        mockSchedule = new MockSchedule();
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
        standingsDb = new StandingsMockDb(0);
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb, output);
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        output = new CmdUserOutput();
        state = new SimulateGameState(leagueToSimulate, schedule, standingsDb, standings, context, utility, "14-11-2020", "", 0, output);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("Injury", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertEquals("Toronto", schedule.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Calgary", schedule.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }

    @Test
    public void getStateNameTest() {
        assertEquals("SimulateGame", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("Negative", state.getNextStateName());
        state.nextState(context);
        assertEquals("Injury", state.getNextStateName());
    }

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }
}