package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.*;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.RegularSeasonSchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.StandingsTest.StandingsMockDb;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    private ILeaguePersistance league;


    @Before
    public void setUp() throws Exception {
        league = new LeagueMockData();
        leagueToSimulate = new LeagueMockData().getTestData();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        calendar = Calendar.getInstance();
        standingsDb = new StandingsMockDb(0);
        schedule = new RegularSeasonSchedule(calendar, output);
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb, output);
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        state = new PersistState(leagueToSimulate, schedule, standingsDb , standings, context, utility, "13-11-2020", "", 0, output);
    }

    @Test
    public void nextStateTest() {
        state.nextState(context);
        assertEquals("AdvanceTime", state.getNextStateName());
        assertNotEquals("Negative", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        leagueToSimulate.setLeagueDb(league);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        state.doProcessing();
        String expected  = "Inside persist state";
        String gotOutput = out.toString().replaceAll("\n", "");
        gotOutput = gotOutput.replaceAll("\r", "");
        assertNotEquals("Inside Negative State", gotOutput);
        assertEquals(expected, gotOutput);
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

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }
}