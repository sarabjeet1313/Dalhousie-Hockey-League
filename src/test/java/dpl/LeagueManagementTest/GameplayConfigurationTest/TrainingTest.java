package dpl.LeagueManagementTest.GameplayConfigurationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.GameplayConfiguration.IGameplayConfigurationAbstractFactory;
import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;

public class TrainingTest {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	private IGameplayConfigurationAbstractFactory gameConfig = SystemConfig.getSingleInstance()
			.getGameplayConfigurationAbstractFactory();
	private League leagueToSimulate;
	private Training training;
	private Player player;
	private Coach coach;

	@Before
	public void setUp() throws Exception {
		leagueToSimulate = new LeagueMockData().getTestData();
		training = gameConfig.Training(100, 100);
		player = teamManagement.PlayerWithParameters("Player One", "forward", true, 1, 1, 1, 1, 1, false, false, 0,
				false, 23, 3, 1999, Boolean.FALSE);
		coach = teamManagement.CoachWithParameters("Coach One", 0.5, 0.5, 0.5, 0.5);
	}

	@Test
	public void parameterizedConstructorTest() {
		assertEquals(100, training.getDaysUntilStatIncreaseCheck());
		assertEquals(100, training.getTrackDays());
	}
	
	@Test
	public void getDaysUntilStatIncreaseCheckTest() {
		assertEquals(100, training.getDaysUntilStatIncreaseCheck());
		assertNotEquals(200, training.getDaysUntilStatIncreaseCheck());
	}

	@Test
	public void setDaysUntilStatIncreaseCheckTest() {
		assertEquals(100, training.getDaysUntilStatIncreaseCheck());
		training.setDaysUntilStatIncreaseCheck(150);
		assertEquals(150, training.getDaysUntilStatIncreaseCheck());
	}
	
	@Test
	public void getTrackDaysTest() {
		assertEquals(100, training.getTrackDays());
		assertNotEquals(200, training.getTrackDays());
	}
	
	@Test
	public void setTrackDaysTest() {
		assertEquals(100, training.getTrackDays());
		training.setTrackDays(150);
		assertEquals(150, training.getTrackDays());
	}

	@Test
	public void trackDaysForTrainingTest() {
		Training tempTraining = leagueToSimulate.getGameConfig().getTraining();
		assertEquals(tempTraining.getDaysUntilStatIncreaseCheck(), tempTraining.getTrackDays());
		leagueToSimulate = tempTraining.trackDaysForTraining(leagueToSimulate);
		assertNotEquals(tempTraining.getDaysUntilStatIncreaseCheck(), tempTraining.getTrackDays());
	}
}
