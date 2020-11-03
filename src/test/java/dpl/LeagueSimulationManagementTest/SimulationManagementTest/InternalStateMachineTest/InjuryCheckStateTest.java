package dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachineTest;

import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.*;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.ISchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.RegularSeasonSchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.ScheduleTest.MockSchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.InjuryManagement;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class InjuryCheckStateTest {
    private IUserInput input;
    private IUserOutput output;
    private League leagueToSimulate;
    private ISchedule schedule;
    private MockSchedule mockSchedule;
    private InjuryManagement injury;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private InjuryCheckState state;
    private Calendar calendar;

    @Before
    public void setUp() throws Exception {
        input = new CmdUserInput();
        output = new CmdUserOutput();
        calendar = Calendar.getInstance();
        schedule = new RegularSeasonSchedule(calendar, output);
        injury = new InjuryManagement();
        mockSchedule = new MockSchedule();
        leagueToSimulate = new LeagueMockData().getTestData();
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        state = new InjuryCheckState(leagueToSimulate, injury, schedule, context, utility, "14-11-2020", output);
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
    }

    @Test
    public void nextStateTest() {
        assertNotEquals("Trading", state.getNextStateName());
        state.nextState(context);
        assertEquals("SimulateGame", state.getNextStateName());
    }

    @Test
    public void doProcessingTest() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue(state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getStateNameTest() {
        assertEquals("Injury", state.getStateName());
        assertNotEquals("Trading", state.getStateName());
    }

    @Test
    public void getUpdatedLeague() {
        state.doProcessing();
        assertFalse(null == state.getUpdatedLeague());
        assertTrue( state.getUpdatedLeague() instanceof League);
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("Trading", state.getNextStateName());
        state.nextState(context);
        assertEquals("SimulateGame", state.getNextStateName());
    }
}