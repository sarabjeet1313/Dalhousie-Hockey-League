package dpl.InitializeModelsTest;

import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.InitializeModels.InitializeLeagues;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.TeamManagementTest.CoachMockData;
import dpl.TeamManagementTest.GamaplayConfigMockData;
import dpl.TeamManagementTest.LeagueMockData;
import dpl.TeamManagementTest.ManagerMockData;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserInput.IUserInput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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