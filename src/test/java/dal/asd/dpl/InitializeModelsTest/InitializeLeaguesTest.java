package dal.asd.dpl.InitializeModelsTest;

import dal.asd.dpl.InitializeModels.InitializeLeagues;
import dal.asd.dpl.UserInput.CmdUserInput;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.teammanagement.ILeague;
import dal.asd.dpl.teammanagement.LeagueMockData;
import dal.asd.dpl.teammanagement.Leagues;
import org.junit.Test;
import org.junit.Before;

import java.io.File;

import static org.junit.Assert.*;

public class InitializeLeaguesTest {

    private static InitializeLeagues league;

    @Before
    public void setUpClass() throws Exception {
        ILeague leagueDb = new LeagueMockData();
        IUserOutput output = new CmdUserOutput();
        IUserInput input = new CmdUserInput();

        File file = new File("resources/json/input.json");
        String filePath = file.getCanonicalPath();

        league = new InitializeLeagues(filePath, leagueDb, output, input);
    }

    @Test
    public void isEmptyStringTest() {
        String testingString = "";
        assertTrue(league.isEmptyString(testingString));

        testingString = null;
        assertTrue(league.isEmptyString(testingString));
    }

    @Test
    public void truncateStringTest(){
        String testingString = "testing";
        assertEquals("testing",league.truncateString(testingString));
    }

    @Test
    public void parseAndInitializeModelsTest(){
        Leagues outputLeague = league.parseAndInitializeModels();

        assertEquals("Dalhousie Hockey League", outputLeague.getLeagueName());
        assertEquals("Eastern Conference", outputLeague.getConferenceList().get(0).getConferenceName());
    }

}