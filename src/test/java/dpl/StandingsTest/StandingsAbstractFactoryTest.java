package dpl.StandingsTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.*;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;
import dpl.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandingsAbstractFactoryTest {

    IStandingsAbstractFactory factory;
    IUserOutput output;
    IStandingsPersistance standingsDb;
    League leagueToSimulate;
    StandingInfo standings;

    @Before
    public void setUp() throws Exception {
        factory = SystemConfig.getSingleInstance().getStandingsAbstractFactory();
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
        standingsDb = StandingsMockDb.getInstance();
        leagueToSimulate = LeagueMockData.getInstance().getTestData();
        standings = SystemConfig.getSingleInstance().getStandingsAbstractFactory().StandingInfo(leagueToSimulate, 0, standingsDb, output);
    }

    @Test
    public void standingInfoTest() {
        StandingInfo info = factory.StandingInfo(leagueToSimulate, 0, standingsDb, output);
        assertTrue(info instanceof StandingInfo);
    }

    @Test
    public void standingTest() {
        Standing standing = factory.Standing();
        assertTrue(standing instanceof Standing);
    }

    @Test
    public void teamStandingTest() {
        TeamStanding teamStanding = factory.TeamStanding();
        assertTrue(teamStanding instanceof TeamStanding);
    }
}