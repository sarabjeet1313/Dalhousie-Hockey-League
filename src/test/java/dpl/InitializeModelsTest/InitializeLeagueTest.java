package dpl.InitializeModelsTest;

import dpl.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.InitializeModels.InitializeLeagues;
import dpl.TeamManagement.ICoachPersistance;
import dpl.TeamManagement.ILeaguePersistance;
import dpl.TeamManagement.IManagerPersistance;
import dpl.TeamManagement.League;
import dpl.TeamManagementTest.CoachMockData;
import dpl.TeamManagementTest.GamaplayConfigMockData;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.TeamManagementTest.ManagerMockData;
import dpl.UserInput.CmdUserInput;
import dpl.UserInput.IUserInput;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

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