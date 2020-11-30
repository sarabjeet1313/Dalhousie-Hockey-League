package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.SimulateRegularSeasonMatch;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.ScheduleTest.MockSchedule;
import dpl.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import dpl.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SimulateRegularSeasonMatchTest {

    private SimulateRegularSeasonMatch regularSeasonMatch;
    private IUserOutput output;
    private ISchedule schedule;
    private League leagueToSimulate;
    private MockSchedule mockSchedule;
    private Calendar calendar;
    private StandingInfo standings;
    private IStandingsPersistance standingsDb;

    @Before
    public void setUp() throws Exception {
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        mockSchedule = MockSchedule.getInstance();
        leagueToSimulate = LeagueMockData.getInstance().getTestData();
        calendar = Calendar.getInstance();
        schedule = SystemConfig.getSingleInstance().getScheduleAbstractFactory().RegularSeasonSchedule(calendar, output);
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
        standingsDb = StandingsMockDb.getInstance();
        standings = SystemConfig.getSingleInstance().getStandingsAbstractFactory().StandingInfo(leagueToSimulate, 0, standingsDb, output);
        regularSeasonMatch = (SimulateRegularSeasonMatch) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().SimulateRegularSeasonMatch("14-11-2020", schedule, output, leagueToSimulate, standings);
    }

    @Test
    public void simulateMatchTest() {
        regularSeasonMatch.simulateMatch();
        assertEquals(1, schedule.getFinalSchedule().size());
        assertNotEquals(2, schedule.getFinalSchedule().size());
    }
}