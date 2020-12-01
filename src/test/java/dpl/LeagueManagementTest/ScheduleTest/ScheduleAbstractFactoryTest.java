package dpl.LeagueManagementTest.ScheduleTest;

import dpl.LeagueManagement.Schedule.*;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.LeagueManagementTest.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleAbstractFactoryTest {

    IScheduleAbstractFactory factory;
    IUserOutput output;
    IStandingsPersistance standingsDb;
    League leagueToSimulate;
    StandingInfo standings;

    @Before
    public void setUp() throws Exception {
        factory = SystemConfig.getSingleInstance().getScheduleAbstractFactory();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        standingsDb = StandingsMockDb.getInstance();
        leagueToSimulate = LeagueMockData.getInstance().getTestData();
        standings = SystemConfig.getSingleInstance().getStandingsAbstractFactory().StandingInfo(leagueToSimulate, 0, standingsDb, output);
    }

    @Test
    public void playoffScheduleTest() {
        ISchedule schedule = factory.PlayoffSchedule(output, standingsDb, standings, 0);
        assertTrue(schedule instanceof PlayoffSchedule);
        assertFalse(schedule instanceof RegularSeasonSchedule);
    }

    @Test
    public void seasonCalendarTest() {
        SeasonCalendar calendar = factory.SeasonCalendar(0, output);
        assertTrue(calendar instanceof SeasonCalendar);
    }

    @Test
    public void regularSeasonScheduleTest() {
        ISchedule schedule = factory.RegularSeasonSchedule(null, output);
        assertFalse(schedule instanceof PlayoffSchedule);
        assertTrue(schedule instanceof RegularSeasonSchedule);
    }
}