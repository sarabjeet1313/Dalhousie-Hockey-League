package dpl.LeagueSimulationManagementTest.LeagueManagementTest.InitializeModelsTest;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.IGameplayConfigPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.InitializeModels.InitializeLeagues;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.CoachMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.GamaplayConfigMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.LeagueMockData;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest.ManagerMockData;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.CmdUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserInput.IUserInput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

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