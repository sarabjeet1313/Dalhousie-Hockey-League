package dal.asd.dpl.InitializeModelsTest;

import dal.asd.dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dal.asd.dpl.InitializeModels.InitializeLeagues;
import dal.asd.dpl.TeamManagement.ICoachPersistance;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagement.IManagerPersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagementTest.CoachMockData;
import dal.asd.dpl.TeamManagementTest.GamaplayConfigMockData;
import dal.asd.dpl.TeamManagementTest.LeagueMockData;
import dal.asd.dpl.TeamManagementTest.ManagerMockData;
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
		ILeaguePersistance leagueDb = new LeagueMockData();
		ICoachPersistance coachMock = new CoachMockData();
		IGameplayConfigPersistance configMock = new GamaplayConfigMockData();
		IManagerPersistance managerMock = new ManagerMockData();
		IUserOutput output = new CmdUserOutput();
		IUserInput input = new CmdUserInput();
		URL i = getClass().getClassLoader().getResource("input.json");
		String filePath = i.getPath();

		league = new InitializeLeagues(filePath, leagueDb, output, input, coachMock, configMock, managerMock);
	}

	@Test
	public void isEmptyStringTest() {
		String testingString = "";
		assertTrue(league.isEmptyString(testingString));
	}

	@Test
	public void truncateStringTest() {
		String testingString = "testing";
		assertEquals("testing", league.truncateString(testingString));
	}

	@Test
	public void parseAndInitializeModelsTest() {
		League outputLeague = league.parseAndInitializeModels();
		assertEquals("test Hockey League", outputLeague.getLeagueName());
		assertEquals("Eastern Conference", outputLeague.getConferenceList().get(0).getConferenceName());
	}

}