package GameplayConfigurationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dal.asd.dpl.GameplayConfiguration.Training;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagementTest.LeagueObjectTestData;

public class TrainingTest {

	League mockData = new LeagueObjectTestData().getLeagueData();
	Training training = new Training(100, 100);
	Player player = new Player("Player One", "forward", true, 1, 1, 1, 1, 1, false, false, 0);
	Coach coach = new Coach("Coach One", 0.5, 0.5, 0.5, 0.5);

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
	public void updateStatsTest() {
//		double beforeStatUpdate = player.getShooting() + player.getSkating() + player.getChecking() + player.getSaving();
//		training.updateStats(player, coach, mockData);
//		double afterStatUpdate = player.getShooting() + player.getSkating() + player.getChecking() + player.getSaving();
//		assertFalse(afterStatUpdate >= beforeStatUpdate);
	}

	@Test
	public void playerTrainingTest() {

	}
}
