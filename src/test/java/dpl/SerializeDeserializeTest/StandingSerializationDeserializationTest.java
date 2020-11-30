package dpl.SerializeDeserializeTest;

import dpl.LeagueSimulationManagement.LeagueManagement.Standings.Standing;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SerializeDeserialize.StandingSerializationDeserialization;
import dpl.StandingsTest.StandingsMock;
import dpl.StandingsTest.StandingsMockDb;
import dpl.SystemConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandingSerializationDeserializationTest {

    private StandingSerializationDeserialization standingSerializationDeserialization;
    private Standing standing;
    private StandingsMockDb standingsMock;
    private IUserOutput output;

    @Before
    public void setUp() throws Exception {
        standingSerializationDeserialization = SystemConfig.getSingleInstance().getSerializeDeserializeAbstractFactory().StandingSerializationDeserialization();
        standing = SystemConfig.getSingleInstance().getStandingsAbstractFactory().Standing();
        standingsMock = new StandingsMockDb(1);
        standing.setSeason(1);
        standing.setStandings(standingsMock.getTeamStandings());
        output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
    }

    @Test
    public void setSeasonTest() {
        assertNotEquals(2, standingSerializationDeserialization.getSeason());
        standingSerializationDeserialization.setSeason(2);
        assertEquals(2, standingSerializationDeserialization.getSeason());
    }

    @Test
    public void getSeasonTest() {
        assertNotEquals(2, standingSerializationDeserialization.getSeason());
        standingSerializationDeserialization.setSeason(2);
        assertEquals(2, standingSerializationDeserialization.getSeason());
    }

    @Test
    public void insertToStandingsTest() {
        try {
            assertTrue(standingSerializationDeserialization.insertToStandings(standing));
        } catch (Exception e) {
            output.setOutput(e.getMessage());
            output.sendOutput();
        }
    }
}