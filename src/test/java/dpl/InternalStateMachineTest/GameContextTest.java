package dpl.InternalStateMachineTest;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SimulationManagement.InternalStateMachine.GameContext;
import dpl.SimulationManagement.InternalStateMachine.ISimulateMatch;
import dpl.SimulationManagement.InternalStateMachine.SimulatePlayoffSeasonMatch;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.ScheduleTest.MockSchedule;
import dpl.SystemConfig;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class GameContextTest {

    private ISimulateMatch match;
    private IUserOutput output;
    private League leagueToSimulate;
    private GameContext context;
    private ISchedule schedule;
    private MockSchedule mockSchedule;
    private Calendar calendar;

    @Before
    public void setUp() throws Exception {
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        context = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().GameContext(match);
        mockSchedule = MockSchedule.getInstance();
        leagueToSimulate = LeagueMockData.getInstance().getTestData();
        calendar = Calendar.getInstance();
        schedule = SystemConfig.getSingleInstance().getScheduleAbstractFactory().RegularSeasonSchedule(calendar, output);
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
        match = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().SimulateRegularSeasonMatch("13-11-2020", schedule, output, leagueToSimulate, null);
    }

    @Test
    public void changeGameTest() {
        assertFalse(context.getMatch() instanceof SimulatePlayoffSeasonMatch);
        match = new SimulatePlayoffSeasonMatch("13-11-2020", schedule, output, leagueToSimulate, null, null);
        context.changeGame(match);
        assertTrue(context.getMatch() instanceof SimulatePlayoffSeasonMatch);

    }

    @Test
    public void simulateMatchTest() {
        context.changeGame(match);
        context.simulateMatch();
        assertEquals(1, schedule.getFinalSchedule().size());
        assertNotEquals(2, schedule.getFinalSchedule().size());
    }

    @Test
    public void getMatchTest() {
        assertFalse(context.getMatch() instanceof SimulatePlayoffSeasonMatch);
        match = new SimulatePlayoffSeasonMatch("", schedule, output, leagueToSimulate, null, null);
        context.changeGame(match);
        assertTrue(context.getMatch() instanceof SimulatePlayoffSeasonMatch);
    }
}