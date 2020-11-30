package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.SimulatePlayoffSeasonMatch;
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

public class SimulatePlayoffSeasonMatchTest {

    private SimulatePlayoffSeasonMatch playoffSeasonMatch;
    private IUserOutput output;
    private ISchedule schedule;
    private League leagueToSimulate;
    private MockSchedule mockSchedule;
    private Calendar calendar;
    private StandingInfo standings;
    private IStandingsPersistance standingsDb;
    private SeasonCalendar utility;

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
        utility = SystemConfig.getSingleInstance().getScheduleAbstractFactory().SeasonCalendar(0, output);
        playoffSeasonMatch = (SimulatePlayoffSeasonMatch) SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory().SimulatePlayoffSeasonMatch("14-11-2020", schedule, output, leagueToSimulate, standings, utility);
    }

    @Test
    public void simulateMatchTest() {
        playoffSeasonMatch.simulateMatch();
        assertEquals(1, schedule.getFinalSchedule().size());
        assertNotEquals(2, schedule.getFinalSchedule().size());
    }
}