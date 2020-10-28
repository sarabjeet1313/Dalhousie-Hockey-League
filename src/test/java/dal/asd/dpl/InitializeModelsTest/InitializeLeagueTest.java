package dal.asd.dpl.InitializeModelsTest;

import dal.asd.dpl.InitializeModels.InitializeLeagues;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.LeagueMockData;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;

import org.junit.Test;
import org.junit.Before;

import java.net.URL;

import static org.junit.Assert.*;

public class InitializeLeagueTest {

    private static InitializeLeagues league;

    @Before
    public void setUpClass() throws Exception {
        ILeague leagueDb = new LeagueMockData();
        IUserOutput output = new CmdUserOutput();
        IUserInput input = new CmdUserInput();
        URL i = getClass().getClassLoader().getResource("input.json");
        String filePath = i.getPath();

        league = new InitializeLeagues(filePath, leagueDb, output, input);
    }

    @Test
    public void isEmptyStringTest() {
        String testingString = "";
        assertTrue(league.isEmptyString(testingString));
    }

    @Test
    public void truncateStringTest(){
        String testingString = "testing";
        assertEquals("testing",league.truncateString(testingString));
    }

    @Test
    public void parseAndInitializeModelsTest(){
        League outputLeague = league.parseAndInitializeModels();
        assertEquals("Dal Hockey League", outputLeague.getLeagueName());
        assertEquals("Eastern Conference", outputLeague.getConferenceList().get(0).getConferenceName());
    }

}