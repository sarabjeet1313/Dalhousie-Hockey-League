package dpl.GameplayConfigurationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.TeamManagementTest.LeagueObjectTestData;

public class TrainingTest {
	
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	League mockData = new LeagueObjectTestData().getLeagueData();
	Training training = new Training(100, 100);
	Player player = teamManagement.PlayerWithParameters("Player One", "forward", true, 1, 1, 1, 1, 1, false, false, 0, false, 23, 3, 1999);
	Coach coach = teamManagement.CoachWithParameters("Coach One", 0.5, 0.5, 0.5, 0.5);

	@Test
	public void getDaysUntilStatIncreaseCheckTest() {
		assertEquals(100, mockData.getGameConfig().getTraining().getDaysUntilStatIncreaseCheck());
	}

	@Test
	public void setDaysUntilStatIncreaseCheckTest() {
		mockData.getGameConfig().getTraining().setDaysUntilStatIncreaseCheck(50);
		assertEquals(50, mockData.getGameConfig().getTraining().getDaysUntilStatIncreaseCheck());
	}

	@Test
	public void generateRandomValueTest() {
		assertTrue((0 < training.generateRandomValue()) && (training.generateRandomValue() < 1));
	}

	@Test
	public void playerTrainingTest() {

	}
}
