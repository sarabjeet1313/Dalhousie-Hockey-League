package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.GameContext;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.ISimulateMatch;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.SimulatePlayoffSeasonMatch;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.ScheduleTest.MockSchedule;
import dpl.SystemConfig;
import dpl.TeamManagementTest.LeagueMockData;
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
        assertEquals("Halifax", schedule.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Calgary", schedule.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }

    @Test
    public void getMatchTest() {
        assertFalse(context.getMatch() instanceof SimulatePlayoffSeasonMatch);
        match = new SimulatePlayoffSeasonMatch("", schedule, output, leagueToSimulate, null, null);
        context.changeGame(match);
        assertTrue(context.getMatch() instanceof SimulatePlayoffSeasonMatch);
    }
}