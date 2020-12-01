package dpl.LeagueManagementTest.InitializeModelsTest;

import dpl.LeagueManagementTest.GameplayConfigurationTest.GameplayConfigMockData;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueManagement.InitializeModels.InitializeLeagues;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagementTest.TeamManagementTest.CoachMockData;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueManagementTest.TeamManagementTest.ManagerMockData;
import dpl.UserInputOutput.UserInput.CmdUserInput;
import dpl.UserInputOutput.UserInput.IUserInput;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

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
		IGameplayConfigPersistance configMock = new GameplayConfigMockData();
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