package dpl.TeamManagementTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IManagerPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class ManagerTest {

	private League leagueToSimulate;
	private Manager manager;
	private Manager manager1;
	private final String MANAGER_NAME = "Tom Spaghetti";
	private final String TYPE = "normal";
	private final String TEST = "normal";
	private final String TEAM_NAME = "Boston";
	private final String TEAM_MANAGER = "Karen Potam";
	private IUserOutput output = new CmdUserOutput();
	private IManagerPersistance managerMock;
	private ITeamManagementAbstractFactory teamManagement;

	@Before
	public void setUp() throws Exception {
		managerMock = new ManagerMockData();
		teamManagement = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		leagueToSimulate = new LeagueMockData().getTestData();
		manager = leagueToSimulate.getManagerList().get(0);
		manager1 = teamManagement.ManagerWithDbParameters(manager.getManagerName(), manager.getManagerPersonality(), managerMock);
	}

	@Test
	public void parameterizedConstructorTest() {
		assertEquals(MANAGER_NAME, manager.getManagerName());
		assertEquals(TYPE, manager.getManagerPersonality());
	}

	@Test
	public void getManagerNameTest() {
		assertEquals(MANAGER_NAME, manager.getManagerName());
		assertNotEquals(TEST, manager.getManagerName());
	}

	@Test
	public void setManagerNameTest() {
		manager.setManagerName(TEST);
		assertEquals(TEST, manager.getManagerName());
	}

	@Test
	public void getManagerPersonalityTest() {
		assertEquals(TYPE, manager.getManagerPersonality());
		assertNotEquals(MANAGER_NAME, manager.getManagerPersonality());
	}

	@Test
	public void setManagerPersonalityTest() {
		manager.setManagerPersonality(MANAGER_NAME);
		assertEquals(MANAGER_NAME, manager.getManagerName());
	}

	@Test
	public void saveTeamGeneralManagerTest() {
		try {
			assertTrue(manager1.saveManagerList(leagueToSimulate));
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	@Test
	public void getMangerPersonalityByTeamTest() {
		assertEquals(TYPE, manager.getMangerPersonalityByTeam(TEAM_NAME, leagueToSimulate));
		assertNotEquals(TEAM_NAME, manager.getMangerPersonalityByTeam(TEAM_NAME, leagueToSimulate));
	}

	@Test
	public void saveManagerListTest() {
		try {
			assertTrue(manager1.saveManagerList(leagueToSimulate));
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

}
