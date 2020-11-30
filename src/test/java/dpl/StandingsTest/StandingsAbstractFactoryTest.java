package dpl.StandingsTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.Standing;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingsAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.TeamStanding;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.TeamManagementTest.LeagueMockData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandingsAbstractFactoryTest {

    StandingsAbstractFactory factory;
    IUserOutput output;
    IStandingsPersistance standingsDb;
    League leagueToSimulate;
    StandingInfo standings;

    @Before
    public void setUp() throws Exception {
        factory = new StandingsAbstractFactory();
        output = new CmdUserOutput();
        standingsDb = new StandingsMockDb(0);
        leagueToSimulate = new LeagueMockData().getTestData();
        standings = new StandingInfo(leagueToSimulate, 0, standingsDb, output);
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