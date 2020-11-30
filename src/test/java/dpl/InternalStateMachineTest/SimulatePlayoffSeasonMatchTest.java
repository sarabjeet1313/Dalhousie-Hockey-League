package dpl.InternalStateMachineTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.RegularSeasonSchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine.SimulatePlayoffSeasonMatch;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.ScheduleTest.MockSchedule;
import dpl.StandingsTest.StandingsMockDb;
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
        output = new CmdUserOutput();
        mockSchedule = new MockSchedule();
        leagueToSimulate = new LeagueMockData().getTestData();
        calendar = Calendar.getInstance();
        schedule = new RegularSeasonSchedule(calendar, output);
        schedule.setFinalSchedule(mockSchedule.getMockSchedule());
        standingsDb = new StandingsMockDb(0);
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb, output);
        utility = new SeasonCalendar(0, output);
        playoffSeasonMatch = new SimulatePlayoffSeasonMatch("14-11-2020", schedule, output, leagueToSimulate, standings, utility);
    }

    @Test
    public void simulateMatchTest() {
        playoffSeasonMatch.simulateMatch();
        assertEquals("Halifax", schedule.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
        assertNotEquals("Calgary", schedule.getFinalSchedule().get("14-11-2020").get(0).get("Boston"));
    }
}