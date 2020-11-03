package dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachineTest;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagementTest.SimulationManagementTest.InternalStateMachine.*;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.ISchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.RegularSeasonSchedule;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.ScheduleTest.MockSchedule;
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

public class TrainingStateTest {

    private TrainingState state;
    private League leagueToSimulate;
    private LeagueMockData mockLeague;
    private ISchedule schedule;
    private MockSchedule mockSchedule;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private IUserOutput output;
    private IUserInput input;
    private Calendar calendar;
    private Training training;

    @Before
    public void setUp() throws Exception {
        mockLeague = new LeagueMockData();
        input = new CmdUserInput();
        output = new CmdUserOutput();
        leagueToSimulate = mockLeague.getTestData();
        calendar = Calendar.getInstance();
        schedule = new RegularSeasonSchedule(calendar, output);
        mockSchedule = new MockSchedule();
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
        context = new InternalStateContext(input, output);
        utility = new SeasonCalendar(0, output);
        training = new Training(100, 100);
        state = new TrainingState(leagueToSimulate, training, schedule, utility, "14-11-2020", output, context);
    }

    @Test
    public void nextStateTest() {
        context.setState(state);
        context.nextState();
        assertEquals("SimulateGame", state.getNextStateName());
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
        assertEquals("Training", state.getStateName());
        assertNotEquals("Negative", state.getStateName());
    }

    @Test
    public void getNextStateNameTest() {
        assertNotEquals("SimulateGame", state.getNextStateName());
        state.nextState(context);
        assertEquals("SimulateGame", state.getNextStateName());
    }
}