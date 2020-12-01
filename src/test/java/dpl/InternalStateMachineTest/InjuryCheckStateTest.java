package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InjuryCheckState;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.InternalStateContext;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.ScheduleTest.MockSchedule;
import dpl.SystemConfig;
import dpl.TeamManagementTest.LeagueMockData;
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
    private IInjuryManagement injury;
    private InternalStateContext context;
    private SeasonCalendar utility;
    private InjuryCheckState state;
    private Calendar calendar;
    private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();

    @Before
    public void setUp() throws Exception {
        input = SystemConfig.getSingleInstance().getUserInputAbstractFactory().CmdUserInput();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        calendar = Calendar.getInstance();
        schedule = SystemConfig.getSingleInstance().getScheduleAbstractFactory().RegularSeasonSchedule(calendar, output);
        injury = teamManagement.InjuryManagement();
        mockSchedule = MockSchedule.getInstance();
        leagueToSimulate = LeagueMockData.getInstance().getTestData();
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InternalStateContext(input, output);
        utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        state = (InjuryCheckState) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().InjuryCheckState(leagueToSimulate, injury, schedule, context, utility, "14-11-2020", null, 0, output, null, null);
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
    public void getUpdatedLeagueTest() {
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

    @Test
    public void shouldContinueTest() {
        assertTrue(state.shouldContinue());
        assertFalse(!state.shouldContinue());
    }
}